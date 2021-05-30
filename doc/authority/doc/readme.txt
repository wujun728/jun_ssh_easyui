一、关于数据存储
1、数据持久化完全依赖hibernate ,查询时尽量使用BaseJdbcService这个类，以避免可能的性能问题
2、保存和更新推荐使用hibernate

二、包结构：com.lostad.app
api: tx
