# 随 心
>刚开始写这个项目的时候是为了自己的毕业设计。
>后来慢慢的就想借这个项目来巩固自己的Android编程技能，也可以更好的站在全局来考虑项目的开发，而不只是需求的迭代开发。

## APP细节

### API
- APP的API
    - 音乐部分，是在网络上收集的网易云音乐的API。用了挺散的， 比如 ： https://github.com/javaSwing/MusicAPI
    - 知乎日报， https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90
    - 段子、图片， API市场
- 对于第三方开源库的使用
    - 基础库： RxJava, Retrofit, fresco, eventbus
    - 侧滑退出
- 项目编码
    - 对于复杂的逻辑， 采用的MVP编写。
    - 由于本人是处女座，代码阅读起来应该还是比较容易的。
- github
    - https://github.com/SusionSuc/Boring
    - 当然还是希望可以给一个star的... 谢谢

### 模块设计架构
- 音乐播放模块的大体架构
    - MusicServie负责维护音乐播放
        - 管理 MediaPlayer
        - 管理 播放队列
    - Client 与 MusicService的通讯
        - MusicService 会开启一个广播接收者，根据相应的广播Action，处理相应的事件
        - 抽取音乐播放Action类， 即发送特定的Action来控制音乐播放
        - Client 通过广播接收者，来更新音乐播放相关UI : 进度、播放状态等
        - Client 通过Action类，向Service的广播接收者发送特定的Action，来实现音乐的控制。

- 阅读模块的大体架构
    - 知乎阅读
        - StickHeader的实现，
        - 对于知乎文章的展示， 利用RxJava请求文章内容，文章的CSS样式， 然后拼接 Html内容，进行展示
    - 段子
        - 普通的Recycler列表
    - 图片
        - 通过分类window来切换图片请求的URI
        - 图片的查看，简单的实现缩放退出
- 整个APP的收藏模块
    - 利用第三方关系型数据库， 泛型， 实现了简单的对象存储
    - 最简单的收藏就是， 收藏：把对象存入数据库， 删除收藏： 把对象从数据库中删除


- 接下来事情不是很多，对整个APP还会慢慢优化。


## APP截图

<img src="./screenshot/mainpage1.jpg" width = "210" height = "375" alt="图片名称" align=center />
<img src="./screenshot/mainpage2.jpg " width = "210" height = "375" alt="图片名称" align=center />
<img src="./screenshot/musicdetail.jpg" width = "210" height = "375" alt="图片名称" align=center />
<img src="./screenshot/playlist.jpg" width = "210" height = "375" alt="图片名称" align=center />
<img src="./screenshot/readingpage.jpg" width = "210" height = "375" alt="图片名称" align=center />
<img src="./screenshot/essaydetail.jpg" width = "210" height = "375" alt="图片名称" align=center />
<img src="./screenshot/drawerpage.jpg" width = "210" height = "375" alt="图片名称" align=center />
<img src="./screenshot/joke.jpg" width = "210" height = "375" alt="图片名称" align=center />
<img src="./screenshot/imagepage.jpg" width = "210" height = "375" alt="图片名称" align=center />
<img src="./screenshot/changepage.jpg" width = "210" height = "375" alt="图片名称" align=center />


