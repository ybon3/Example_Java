Example_Java
============

紀錄一些工作上遇到問題的 workaround 範例


multi_datasource
----------------

實現多 DB 連線的最陽春版，沒搭配 JPA 只使用了 `JdbcTemplate`，並簡單實作自定義的 `AbstractRoutingDataSource`

也簡單實作了 `InterceptorAdapter`，透過 request URI 來決定要使用哪個 DataSource


Prerender
---------

在 NKG 時的小作品，提供 pre-render 服務。

使用了微軟的 playwright 作為 render 的主體，有以下特色：

* 運行於 Docker 方便佈署
* playwright 效率比較快（就當時而言）
* 有做 browser pool 來限制最大同時可產生的視窗來避免 crash

具體實現是直接安裝微軟提供的 docker image，在其 MicroService 中運作本專案程式（Runtime 編譯）

