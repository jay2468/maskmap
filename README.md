# maskmap
口罩地圖APP

使用Kotlin語言以及Android架構組件開發

(data binding,Lifecycle,LiveData,ViewModel,Navigation,Room,WorkManager)

程式架構為使用一個Activity包含數個Fragment,Fragment之間切換使用Navigation,

UI以及數據之間的溝通使用data binding,LiveData以及ViewModel

專案中並使用kotlin 獨有的Coroutine處理異步任務,例如撈取健保署提供的口罩數據並存入SQLite(使用Room)

APP中的數居皆是讀取SQLite中的數據

DI(依賴注入)使用Kodein,以及使用WorkManager從背景定時向後端撈取數據並更新到SQLite中

***使用google map所需的key未提交,所以運行專案無法使用,專案底下apk目錄中有apk檔可供安裝使用***

資料來源: https://g0v.hackmd.io/@kiang/mask-info#%E5%8F%A3%E7%BD%A9%E4%BE%9B%E9%9C%80%E8%B3%87%E8%A8%8A%E5%B9%B3%E5%8F%B0


![image](https://github.com/jay2468/maskmap/blob/master/01.jpg)

![image](https://github.com/jay2468/maskmap/blob/master/02.jpg)

![image](https://github.com/jay2468/maskmap/blob/master/03.jpg)
