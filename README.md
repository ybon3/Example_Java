# Example_Java
紀錄一些工作上遇到問題的 workaround 範例


multi_datasource
----------------

多 DB 連線的最陽春版，沒搭配 JPA 只使用了 `JdbcTemplate`，並簡單實作自定義的 `AbstractRoutingDataSource`

也簡單實作了 `InterceptorAdapter`，透過 request URI 來決定要使用哪個 DataSource
