package apijson.demo.client.util

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import zuo.biao.apijson.JSON
import zuo.biao.apijson.NotNull
import zuo.biao.apijson.StringUtil
import java.io.File
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Type
import java.util.*

object MethodUtil {

    interface Callback {
        fun newSuccessResult(): JSONObject
        fun newErrorResult(e: Exception): JSONObject
    }

    var CODE_SUCCESS = 200
    var CODE_SERVER_ERROR = 500
    var MSG_SUCCESS = "success"
    var CALLBACK: Callback = object: Callback {

        override fun newSuccessResult(): JSONObject {
            var result = JSONObject()
            result["code"] = CODE_SUCCESS
            result["msg"] = MSG_SUCCESS
            return result
        }

        override fun newErrorResult(e: Exception): JSONObject {
            var result = JSONObject()
            result["code"] = CODE_SERVER_ERROR
            result["msg"] = e.message
            return result
        }
    }

    //  Map<package,   <class,     <constructorArgs, instance>>>
    val INSTANCE_MAP: MutableMap<String, MutableMap<String, MutableMap<Any, Any>>>
    val CLASS_MAP: MutableMap<String, Class<*>>

    init {
        INSTANCE_MAP = HashMap()

        CLASS_MAP = HashMap()
        CLASS_MAP[Boolean::class.java.simpleName] = Boolean::class.java
        CLASS_MAP[Int::class.java.simpleName] = Int::class.java
        CLASS_MAP[Long::class.java.simpleName] = Long::class.java
        CLASS_MAP[Float::class.java.simpleName] = Float::class.java
        CLASS_MAP[Double::class.java.simpleName] = Double::class.java
        CLASS_MAP[Boolean::class.java.simpleName] = Boolean::class.java
        CLASS_MAP[Int::class.java.simpleName] = Int::class.java
        CLASS_MAP[Long::class.java.simpleName] = Long::class.java
        CLASS_MAP[Float::class.java.simpleName] = Float::class.java
        CLASS_MAP[Double::class.java.simpleName] = Double::class.java
        CLASS_MAP[String::class.java.simpleName] = String::class.java
        CLASS_MAP[Any::class.java.simpleName] = Any::class.java
        CLASS_MAP[java.lang.reflect.Array::class.java.simpleName] = java.lang.reflect.Array::class.java

        CLASS_MAP[BooleanArray::class.java.simpleName] = BooleanArray::class.java
        CLASS_MAP[IntArray::class.java.simpleName] = IntArray::class.java
        CLASS_MAP[LongArray::class.java.simpleName] = LongArray::class.java
        CLASS_MAP[FloatArray::class.java.simpleName] = FloatArray::class.java
        CLASS_MAP[DoubleArray::class.java.simpleName] = DoubleArray::class.java

        CLASS_MAP[Collection::class.java.simpleName] = Collection::class.java//???????????????<T>
        CLASS_MAP[List::class.java.simpleName] = List::class.java//???????????????<T>
        CLASS_MAP[ArrayList::class.java.simpleName] = ArrayList::class.java//???????????????<T>
        CLASS_MAP[Map::class.java.simpleName] = Map::class.java//???????????????<T>
        CLASS_MAP[HashMap::class.java.simpleName] = HashMap::class.java//???????????????<T>
        CLASS_MAP[Set::class.java.simpleName] = Set::class.java//???????????????<T>
        CLASS_MAP[HashSet::class.java.simpleName] = HashSet::class.java//???????????????<T>

        CLASS_MAP[com.alibaba.fastjson.JSON::class.java.simpleName] = com.alibaba.fastjson.JSON::class.java//????????????Map?????????getLongValue?????????
        CLASS_MAP[JSONObject::class.java.simpleName] = JSONObject::class.java//????????????Map?????????getLongValue?????????
        CLASS_MAP[JSONArray::class.java.simpleName] = JSONArray::class.java//????????????Collection?????????getJSONObject?????????
    }




    /**
     * @param request : {
     * "package": "apijson.demo.server",
     * "class": "DemoFunction",
     * "classArgs": [
     * null,
     * null,
     * 0,
     * null
     * ],
     * "method": "plus",
     * "methodArgs": [
     * {
     * "type": "Integer",  //???????????????????????? value ?????????
     * "value": 1
     * },
     * {
     * "type": "String",
     * "value": "APIJSON"
     * },
     * {
     * "type": "JSONObject",  //????????????JSONObject ???????????? CLASS_MAP
     * "value": {}
     * },
     * {
     * "type": "apijson.demo.server.model.User",  //??????????????????????????????
     * "value": {
     * "id": 1,
     * "name": "Tommy"
     * }
     * }
     * ]
     * }
     * @return
     */
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun invokeMethod(request: String): JSONObject {
        var req: JSONObject? = JSON.parseObject(request)
        if (req == null) {
            req = JSONObject()
        }
        val pkgName = req.getString("package")
        val clsName = req.getString("class")
        val methodName = req.getString("method")

        var result: JSONObject
        try {
            Objects.requireNonNull(pkgName)
            Objects.requireNonNull(clsName)
            Objects.requireNonNull(methodName)

            val classArgs = req.getJSONArray("classArgs")
            val methodArgs = req.getJSONArray("methodArgs")

            val clazz = findClass(pkgName, clsName, false)
                    ?: throw ClassNotFoundException("????????? " + dot2Separator(pkgName) + "/" + clsName + " ???????????????")

            val isStatic = req.getBooleanValue("static")

            var instance: Any? = null

            if (isStatic == false) {  //new ?????????
                var pkgMap: MutableMap<String, MutableMap<Any, Any>>? = INSTANCE_MAP[pkgName]
                if (pkgMap == null) {
                    pkgMap = HashMap()
                    INSTANCE_MAP[pkgName] = pkgMap
                }
                var clsMap: MutableMap<Any, Any>? = pkgMap[clsName]
                if (clsMap == null) {
                    clsMap = HashMap()
                    pkgMap[clsName] = clsMap
                }

                val key = if (classArgs == null || classArgs.isEmpty()) "" else classArgs.toJSONString()
                instance = clsMap[key]  //????????????????????????????????????????????????????????????????????????

                if (instance == null) {
                    if (classArgs == null || classArgs.isEmpty()) {
                        instance = clazz.newInstance()
                    } else { //??????????????????
                        var exactContructor = false  //?????????????????????????????????????????? type ???????????????
                        for (i in classArgs.indices) {
                            val obj = classArgs.getJSONObject(i)
                            if (obj != null && StringUtil.isEmpty(obj.getString("type"), true) == false) {
                                exactContructor = true
                                break
                            }
                        }

                        val classArgTypes = arrayOfNulls<Class<*>>(classArgs.size)
                        val classArgValues = arrayOfNulls<Any>(classArgs.size)
                        initTypesAndValues(classArgs, classArgTypes, classArgValues, exactContructor)

                        if (exactContructor) {  //????????????????????????
                            if (instance == null) {
                                val constructor = clazz.getConstructor(*classArgTypes)
                                instance = constructor.newInstance(*classArgs.toTypedArray())
                            }
                        } else {  //???????????????????????????????????????
                            if (instance == null) {
                                val constructors = clazz.constructors
                                if (constructors != null) {
                                    for (i in constructors.indices) {
                                        if (constructors[i] != null && constructors[i].parameterCount === classArgValues.size) {
                                            try {
                                                instance = constructors[i].newInstance(*classArgValues)
                                                break
                                            } catch (e: Exception) {
                                            }

                                        }
                                    }
                                }
                            }
                        }

                    }
                }

                if (instance == null) { //??????????????????
                    throw NullPointerException("????????? " + dot2Separator(pkgName) + "/" + clsName + " ?????? classArgs ????????????????????????")
                }

                clsMap[key] = instance
            }

            //method argument, types and values
            var types: Array<Class<*>?>? = null
            var args: Array<Any?>? = null

            if (methodArgs != null && methodArgs.isEmpty() == false) {
                types = arrayOfNulls(methodArgs.size)
                args = arrayOfNulls(methodArgs.size)
                initTypesAndValues(methodArgs, types, args, true)
            }

            //TODO method ???????????????
            result = CALLBACK.newSuccessResult()
            result["invoke"] = clazz.getMethod(methodName, *types!!).invoke(instance, *args!!)
            result["watch"] = instance
        } catch (e: Exception) {
            e.printStackTrace()
            var ne: Exception = e
            if (e is NoSuchMethodException) {
                ne = IllegalArgumentException("?????? " + methodName + " ????????????????????? " + pkgName + "/" + clsName + " ??????"
                        + "\n?????????????????????????????????????????????????????????????????????\n" + e.message)
            }
            else if (e is InvocationTargetException) {
                val te = e.targetException
                if (StringUtil.isEmpty(te.message, true) == false) { //?????????????????????throws Exception??????throws Throwable?????????
                    ne = te as? Exception ?: Exception(te.message)
                }
                ne = IllegalArgumentException("?????? " + methodName + " ????????????????????????????????????"
                        + "\n????????? key:value ???value???????????????????????????????????????????????????\n" + ne.message)
            }

            result = CALLBACK.newErrorResult(ne)
            result["throw"] = ne.javaClass.name
            result["cause"] = ne.cause
            result["trace"] = ne.stackTrace
        }

        return result
    }


    /**???????????????
     * @param request : {
     * "sync": true,  //??????????????????
     * "package": "apijson.demo.server",
     * "class": "DemoFunction",
     * "method": "plus",
     * "types": ["Integer", "String", "com.alibaba.fastjson.JSONObject"]
     * //???????????????????????????????????????		    "return": true,  //?????? class list???????????????
     * }
     * @return
     */
    fun listMethod(request: String): JSONObject {
        var result: JSONObject

        try {
            var req: JSONObject? = JSON.parseObject(request)
            if (req == null) {
                req = JSONObject()
            }
            val sync = req.getBooleanValue("sync")
            //			boolean returnList = req.getBooleanValue("return");
            val pkgName = req.getString("package")
            val clsName = req.getString("class")
            val methodName = req.getString("method")
            var methodArgTypes: JSONArray? = null

            val allMethod = StringUtil.isEmpty(methodName, true)

            var argTypes: Array<Class<*>?>? = null
            if (allMethod == false) {
                methodArgTypes = req.getJSONArray("types")
                if (methodArgTypes != null && methodArgTypes.isEmpty() == false) {
                    argTypes = arrayOfNulls(methodArgTypes.size)

                    for (i in methodArgTypes.indices) {
                        argTypes[i] = getType(methodArgTypes.getString(i), null, true)
                    }
                }
            }

            val classlist = findClassList(pkgName, clsName, true)
            var list: JSONArray? = null
            if (classlist != null) {
                list = JSONArray(classlist.size)

                for (cls in classlist) {
                    if (cls == null) {
                        continue
                    }

                    val clsObj = JSONObject(true)

                    clsObj["name"] = cls.simpleName
                    clsObj["type"] = trimType(cls.genericSuperclass)
                    clsObj["package"] = dot2Separator(cls.getPackage().name)

                    var methodList: JSONArray? = null
                    if (allMethod == false && argTypes != null && argTypes!!.size > 0) {
                        val mObj = parseMethodObject(cls.getMethod(methodName, *argTypes))
                        if (mObj != null) {
                            methodList = JSONArray(1)
                            methodList.add(mObj)
                        }
                    } else {
                        val methods = cls.methods
                        if (methods != null && methods.size > 0) {
                            methodList = JSONArray(methods.size)

                            for (m in methods) {
                                if (m == null) {
                                    continue
                                }
                                if (allMethod || methodName == m.name) {
                                    methodList.add(parseMethodObject(m))
                                }
                            }
                        }
                    }
                    clsObj["methodList"] = methodList  //???????????????????????????????????????????????????????????? UI	clsObj.put("Method[]", JSON.parseArray(methods));

                    list.add(clsObj)


                    //?????????????????????????????????  FIXME
                    if (sync) {

                    }

                }

            }

            result = CALLBACK.newSuccessResult()
            //			if (returnList) {
            result["classList"] = list  //????????? Class	???????????? name		result.put("Class[]", JSON.parseArray(JSON.toJSONString(classlist)));
            //			}
        } catch (e: Exception) {
            e.printStackTrace()
            result = CALLBACK.newErrorResult(e)
        }

        return result
    }


    private fun dot2Separator(name: String?): String? {
        return name?.replace("\\.".toRegex(), File.separator)
    }

    //	private void initTypesAndValues(JSONArray methodArgs, Class<?>[] types, Object[] args)
    //			throws IllegalArgumentException, ClassNotFoundException, IOException {
    //		initTypesAndValues(methodArgs, types, args, false);
    //	}

    @Throws(IllegalArgumentException::class, ClassNotFoundException::class)
    fun initTypesAndValues(methodArgs: JSONArray?, types: Array<Class<*>?>?, args: Array<Any?>?, defaultType: Boolean) {
        if (methodArgs == null || methodArgs.isEmpty()) {
            return
        }
        if (types == null || args == null) {
            throw IllegalArgumentException("types == null || args == null !")
        }
        if (types!!.size != methodArgs.size || args!!.size != methodArgs.size) {
            throw IllegalArgumentException("methodArgs.isEmpty() || types.length != methodArgs.size() || args.length != methodArgs.size() !")
        }

        var argObj: JSONObject?

        var typeName: String?
        var value: Any?
        for (i in methodArgs.indices) {
            argObj = methodArgs.getJSONObject(i)

            typeName = argObj?.getString("type")
            value = if (argObj == null) null else argObj["value"]

            types[i] = getType(typeName, value, defaultType)
            args[i] = value
        }
    }

    fun parseMethodObject(m: Method?): JSONObject? {
        if (m == null) {
            return null
        }
        //?????? private ??? protected ????????????????????????????????????????????? IDE ???????????????????????????????????????
        /*
		  public Type $_delegate_$method(Type0 arg0, Type1 arg1...) {
		    Type returnVal = method(arg0, arg1...)
		    if (returnVal instanceof Void) {
		      return new Object[]{ watchVar0, watchVar1... }  //FIXME void ????????????????????????????????????
		    }
		    return returnVal;
		  }
		 */
        val mod = m.modifiers
        if (Modifier.isPrivate(mod) || Modifier.isProtected(mod)) {
            return null
        }

        val obj = JSONObject(true)
        obj["name"] = m.name
        obj["parameterTypeList"] = trimTypes(m.genericParameterTypes)
        obj["returnType"] = trimType(m.genericReturnType)
        obj["static"] = Modifier.isStatic(m.modifiers)
        obj["exceptionTypeList"] = trimTypes(m.genericExceptionTypes)
        return obj
    }

    private fun trimTypes(types: Array<Type>?): Array<String?>? {
        if (types != null && types!!.size > 0) {
            val names = arrayOfNulls<String>(types!!.size)
            for (i in types!!.indices) {
                names[i] = trimType(types!![i])
            }
            return names
        }
        return null
    }

    @TargetApi(28)
    private fun trimType(type: Type?): String? {
        return trimType(type?.typeName)
    }

    private fun trimType(name: String?): String? {
        var name = name
        if (name == null || "void" == name) {
            return null
        }

        val set = CLASS_MAP.entries
        for ((key, value) in set) {
            if (name == value.name) {
                return key
            }
        }

        var child = ""
        var index: Int
        do {
            index = name!!.indexOf("<")
            if (index < 0) {
                break
            }
            child += "<" + trimType(name.substring(index + 1, name.lastIndexOf(">"))) + ">"
            name = name.substring(0, index)
        } while (index >= 0)

        if (name!!.startsWith("java.lang.")) {
            name = name.substring("java.lang.".length)
        }
        if (name.startsWith("java.util.")) {
            name = name.substring("java.util.".length)
        }
        if (name.startsWith("com.alibaba.fastjson.")) {
            name = name.substring("com.alibaba.fastjson.".length)
        }

        return dot2Separator(name)!! + child
    }


    //	private Class<?> getType(String name) throws ClassNotFoundException, IOException {
    //		return getType(name, null);
    //	}
    //	private Class<?> getType(String name, Object value) throws ClassNotFoundException, IOException {
    //		return getType(name, value, false);
    //	}
    @Throws(ClassNotFoundException::class)
    fun getType(name: String?, value: Any?, defaultType: Boolean): Class<*>? {
        var name = name
        var type: Class<*>? = null
        if (StringUtil.isEmpty(name, true)) {  //????????????????????????
            if (value == null || defaultType == false) {
                //nothing
            } else {
                type = value.javaClass
            }
        } else {
            type = CLASS_MAP[name]
            if (type == null) {
                name = dot2Separator(name)
                val index = name!!.lastIndexOf(File.separator)
                type = findClass(if (index < 0) "" else name.substring(0, index), if (index < 0) name else name.substring(index + 1), defaultType)

                if (type != null) {
                    CLASS_MAP[name] = type
                }
            }
        }

        if (type == null && defaultType) {
            type = Any::class.java
        }

        return type
    }

    /**
     * ???????????????????????????
     * @param packageName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Throws(ClassNotFoundException::class)
    fun findClass(packageOrFileName: String, @NotNull className: String, ignoreError: Boolean): Class<*>? {
        var className = className
        //????????? Objects.requireNonNull(packageName);
        Objects.requireNonNull(className)

        //FIXME ??????????????? jar ?????????????????? class???????????? ClassLoader.getResource(packageOrFileName) ???????????? null?????????????????????????????????
        try {
            val list = findClassList(packageOrFileName, className, ignoreError)
            val cls = if (list == null || list.isEmpty()) null else list[0]
            if (cls != null) {
                return cls
            }
        } catch (e: Exception) {
        }

        val index = className.indexOf("<")
        if (index >= 0) {
            className = className.substring(0, index)
        }
        //????????????????????? jar ?????????????????????
        return Class.forName(if (StringUtil.isEmpty(packageOrFileName, true)) className else packageOrFileName.replace("/".toRegex(), ".") + "." + className)
    }

    /**
     * @param packageOrFileName
     * @param className
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Throws(ClassNotFoundException::class)
    fun findClassList(packageOrFileName: String, className: String, ignoreError: Boolean): List<Class<*>>? {
        var className = className
        val list = ArrayList<Class<*>>()

        val index = className.indexOf("<")
        if (index >= 0) {
            className = className.substring(0, index)
        }

        val allPackage = StringUtil.isEmpty(packageOrFileName, true)
        val allName = StringUtil.isEmpty(className, true)

        //????????????????????????
        val fileName = if (allPackage) File.separator else dot2Separator(packageOrFileName)

        val loader = Thread.currentThread().contextClassLoader

        //?????? ClassLoader ?????????????????????
        val file: File
        try {
            file = File(loader.getResource(fileName).file)
        } catch (e: Exception) {
            if (ignoreError) {
                return null
            }
            throw e
        }

        val files: Array<File>?
        //		if (allPackage) {  //getResource ???????????????
        files = file.listFiles()
        //		}
        //		else {
        //			files = file.listFiles(new FilenameFilter() {
        //
        //				@Override
        //				public boolean accept(File dir, String name) {
        //					if (fileName.equals(dir.getAbsolutePath())) {
        //
        //					}
        //					return false;
        //				}
        //			});
        //		}

        if (files != null) {
            for (f in files!!) {
                if (f.isDirectory()) {  //????????????????????????????????????
                    if (allPackage) {
                        //???????????????
                        val childList = findClassList(f.getAbsolutePath(), className, ignoreError)
                        if (childList != null && childList.isEmpty() == false) {
                            list.addAll(childList)
                        }
                    }
                } else {  //?????????class??????
                    var name: String? = StringUtil.getTrimedString(f.getName())
                    if (name != null && name.endsWith(".class")) {
                        name = name.substring(0, name.length - ".class".length)
                        if (name.isEmpty() || name == "package-info" || name.contains("$")) {
                            continue
                        }

                        if (allName || className == name) {
                            //???????????????
                            try {
                                val clazz = loader.loadClass(packageOrFileName.replace(File.separator.toRegex(), "\\.") + "." + name)
                                list.add(clazz)

                                if (allName == false) {
                                    break
                                }
                            } catch (e: Exception) {
                                if (ignoreError == false) {
                                    throw e
                                }
                                e.printStackTrace()
                            }

                        }
                    }
                }
            }
        }

        return list
    }

}
