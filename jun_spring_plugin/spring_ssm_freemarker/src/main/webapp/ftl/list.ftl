<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/ftl/css/bootstrap.min.css">
<link rel="stylesheet" href="/ftl/css/laypage.css">

<script src="/ftl/js/vue/vue.min.js"></script>
<script src="/ftl/js/jquery/jquery.min.js"></script>
<script src="/ftl/js/laypage/laypage.js" charset="utf-8"></script>
<script src="/ftl/js/layer/layer.js" charset="utf-8"></script>
</head>
<body>
	<div id="app" class="container">
		${Session["user"].userName}����ӭ��
		
		<form class="form-inline bg-danger" role="form">			
		  ��ѯ������
		  <div class="form-group">
		    <label class="sr-only" for="username">�û�����</label>
		    <input type="text" class="form-control" id="username" placeholder="�û�����">
		  </div>
		  <div class="form-group">
		    <label class="sr-only" for="userage">�û�����</label>
		    <input type="text" class="form-control" id="userage" placeholder="�û�����">
		  </div>
		  <button type="button" id="findUser" class="btn btn-success">��ѯ�û�</button>
		  <button type="button" id="addUserBtn" class="btn btn-danger">�����û�</button>
		</form>
		
		<div class="table-responsive">		
		 	<table class="table">
		 		<thead>
					<tr class="success">						
						<td>�û�</td>		
						<td>����</td>				
						<td>����</td>
					</tr>
				</thead>
				<tbody>
					<tr class="active" v-for="(item,index) in result">
 						<td>{{item.userName}}</td>
						<td>{{item.age}}</td>
						<td><a href="#" @click="editEvent(item.id)">�༭</a>    <a href="#" @click="delEvent(item.id)">ɾ��</a></td>						
					</tr>
				</tbody>
				<tr>
					<td colspan="3"><div id="pagenav"></div></td>
				</tr>
			</table>
			
		</div>
	</div>
	<script>
		var app = new Vue({
			el : '#app',
			data : {
				result : []
			}
		});
	
	
		//��ѯ�û�����
		var getUserPageList = function(curr) {
			$.ajax({
				type : "GET",
				dataType : "json",
				url : "/getPage",
				data : {
					pageNum:curr || 1,
					pageSize:5,
					userName:$("#username").val()
				},
				success : function(msg) {
					app.result=msg.page;
					laypage({
						cont : 'pagenav', //������ֵ֧��id����ԭ��dom����jquery����,
						pages : msg.totalPage, //��ҳ��
						first : '��ҳ',
						last : 'βҳ',
						curr : curr || 1, //��ǰҳ

						jump : function(obj, first) { //������ҳ��Ļص�

							if (!first) { //�����ҳ�����������������ݵ�ǰҳ��obj.curr

								getUserPageList(obj.curr);
							}
						}
					});
					
					
					
				}
			});
		}
		
		getUserPageList();

		//��ѯ�û��¼�

		$('#findUser').on('click', function() {
			getUserPageList();
		});

		
		//�༭�û��¼�

		var editEvent = function(id) {
			layer.open({
				type : 2,
				title : '�༭�û�',
				fix : false,
				maxmin : true,
				shadeClose : true,
				area : [ '1100px', '600px' ],
				content : '/editpage?id='+id,
				end : function() {
					getUserPageList();
				}
			});
		}
	
		//�����û��¼�

		$('#addUserBtn').on('click', function() {			
			layer.open({
				type : 2,
				title : '����û�',
				fix : false,
				maxmin : true,
				shadeClose : true,
				area : [ '1100px', '600px' ],
				content : '/ftl/add.html',
				end : function() {
					getUserPageList();
				}
			});
			
		});
		
		
		//ɾ���û��¼�
		var delEvent = function(id) {
			//ѯ�ʿ�
			layer.confirm('��ȷ��Ҫɾ��ô��', {
				btn : [ '��', '��' ]
			//��ť
			}, function() {
					//��
					$.ajax({
						type : "GET",
						dataType : "json",
						url : "/del",
						data : {
							id:id
						},
						success : function(msg) {
							getUserPageList();
							layer.msg('�Ѿ��ɹ�ɾ����¼' + id,{icon:5});
						}
					});
	
			}, function() {
				//��

			});
		}
		
		
		
		
	</script>
	
	
</body>
</html>