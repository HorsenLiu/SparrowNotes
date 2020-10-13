## 麻雀笔记 SparrowNotes

> 麻雀虽小，五脏俱全

### 1. 软件产品介绍

在快节奏的生活已成为常态的当下，我们每天都不得不接收大量的碎片化信息，这些内容重要且琐碎，如果不能做到有效地整理归纳，就有可能在关键时刻出现纰漏，不仅影响效率，甚至还会在一些场合带来风险。因此，开发一款能够帮助用户整理碎片信息的应用就显得很有必要了。

作为提高效率的信息整理工具，一定要做到尽可能的简单直观，不能给用户带来更多的记忆和处理负担，当用户打开「麻雀笔记」，就可以相当清晰直观地感受到其界面的简洁，干净利落，各类信息直白可见。成功做到了让记录过的内容找得到，看得清，也只有这样才能真正实现它原本的诉求。

早在使用实体记事本记录的时候，很多人就习惯于把一天要做的所有事情都记录进去，想到什么写什么。这种做法虽然简单快速，但并不方便事后查找，并且不同的事项混记在一起，也不方便后续的项目管理，针对这样的问题，「麻雀笔记」把用户可能涉及到的领域进行了梳理，针对不同的项目分别进行归纳。

截至目前，「麻雀笔记」能够处理的碎片信息已经包括了便签、链接、位置、消费、日子、心情、账户、三省等内容，并且针对不同的类型做出不同的型态呈现，比如用户经常用到的日期信息，输入某一个需要记录的重要日子的内容、具体日期等信息后，还可以得到现在距离该日子还有多少天，或者这个日子已经过去了多少天。

和其他备忘录产品类似，进入应用后，可以看到此前收录的内容由上至下依次排列，而在「麻雀笔记」中，你还可以将内容置顶，这样在进入应用的第一时间就可以看到所有置顶的内容，方便区分重要事项和普通事项。

### 2. 相关技术介绍

#### 2.1 ButterKnife

ButterKnife 是一个专注于 Android 系统的 View 注入框架，以前总是要写很多 findViewById 来找到 View 对象，有了 ButterKnife 可以很轻松地省去这些步骤。使用 ButterKnife 对性能基本没有损失，因为 ButterKnife 用到的注解并不是在运行时反射的，而是在编译的时候生成新的 class。项目集成起来特别方便，使用起来也特别简单。

ButterKnife 的优势：

1. 强大的 View 绑定和 Click 事件处理功能，简化代码，提升开发效率
2. 方便地处理 Adapter 里的 ViewHolder 绑定问题
3. 运行时不会影响 APP 效率，使用配置方便
4. 代码清晰，可读性强

#### 2.2 SharedPreferences

SharedPreferences 是使用键值对的方式来存储数据的。当保存一条数据的时候，需要给这条数据提供一个对应的键，这样在读取数据的时候就可以通过这个键把相应的值取出来。而且 SharedPreferences 还支持多种不同的数据类型存储。

#### 2.3 SQLite + OrmLite

SQLite 是一款轻量级的关系型数据库，它的运算速度非常快，占用资源很少，通常只需要几百 KB 的内存就足够了，因而特别适合在移动设备上使用。SQLite 不仅支持标准的 SQL 语法，还遵循了数据库的 ACID 事务，所以只要以前是用过其他的关系型数据库，就可以很快地上手 SQLite。

ORM（全称 Object Relation Mapping）叫做对象关系映射，是一种程序设计技术，用于实现面向对象编程语言中不同类型系统的数据之间的转换。它可以直接将 Bean 文件转换成数据库中的表，Bean 中的属性就是表的列，它将繁琐的数据库操作封装成一个 Dao 类，使用 Dao 类和 Bean 可以直接对数据库进行操作，大多数的方法参数只有一个 Bean 对象。

#### 2.4 多线程编程 Handler + Message

Handler 顾名思义也就是处理者的意思，它主要是用于发送和处理消息的。发送消息一般是使用 Handler 的 sendMessage()方法，而发出的消息经过一系列的辗转处理后，最终会传递到 Handler 的 handleMessage()方法中。

Message 是在线程之间传递的消息，它可以在内部携带少量的信息，用于在不同线程之间交换数据。除了 what 字段，Message 还可以使用 arg1 和 arg2 字段来携带一些整型数据，使用 obj 字段携带一个 Object 对象。

#### 2.5 Glide

Glide 是一款快速高效的开源 Android 媒体管理和图片加载框架，它将媒体解码、内存和磁盘缓存以及资源池打包成一个简单易用的界面。

Glide 支持抓取、解码和显示视频图片、图像和动态 gif。Glide 包括一个灵活的 API，允许开发人员插入几乎任何网络堆栈。默认情况下，Glide 使用了一个定制的基于 HttpUrlConnection 的堆栈，但也包含了可插入谷歌的 Volley 项目或 Square 的 OkHttp 库的实用程序库。

Glide 的主要功能是尽可能平滑快速地滚动任何类型的图像列表，但它也适用于几乎任何需要获取、调整大小和显示远程图像的情况。

#### 2.6 RecyclerView 控件

RecyclerView 是一个增强版的 ListView，不仅可以轻松实现和 ListView 同样的效果，还优化了 ListView 中存在的各种不足之处。同时 RecyclerView 支持横向滚动和瀑布流布局

#### 2.7 Fragment + ViewPager

Fragment 是一种可以嵌入在 Activity 当中的 UI 片段，它能让程序更加合理和充分地利用大屏幕的空间，因而在平板上应用得非常广泛。它和 Activity 同样都能包含布局，同样都有自己的生命周期。甚至可以理解成一个迷你的 Activity。

ViewPager 是 android 扩展包 v4 包中的类，这个类可以让用户左右切换当前的 view。ViewPager 类直接继承了 ViewGroup 类，因此它一个容器类，可以添加其他的 View 类 ViewPager 类需要一个 PagerAdapter 适配器类给它提供数据（类似 RecyclerView）。

ViewPager 经常和 Fragment 一起使用，并且官方还提供了专门的 FragmentPagerAdapter 类供 ViewPager 使用。

#### 2.8 Toolbar 控件

Toolbar 是 Android 5.0 推出的一个 Material Design 风格的导航控件 ,用来取代之前的 Actionbar 。与 Actionbar 相比，Toolbar 明显要灵活的多。它不像 Actionbar 一样，一定要固定在 Activity 的顶部，而是可以放到界面的任意位置。

#### 2.9 TextInputLayout 控件

TextInputLayout 主要是作为 EditText 的容器，从而为 EditText 生成一个浮动的 Label，当用户点击 EditText 的时候，EditText 中的 hint 字符串会自动移到 EditText 的左上角。

#### 2.10 滑动菜单 DrawerLayout + NavigationView

滑动菜单就是将一些菜单选项隐藏起来，而不是放在主屏幕上，然后可以通过滑动的方式将菜单显示出来。

DrawerLayout 是一个布局，在布局中允许放入两个直接子控件，第一个子控件是主屏幕中显示的内容，第二个子控件是滑动菜单中显示的内容。

NavigationView 是 Design Support 库中提供的一个控件，它不仅是严格按照 Material Design 的要求来进行设计的，而且还可以将滑动菜单页面的实现变得简单。

#### 2.11 CircleImageView 控件

CircleImageView 是一个用于将图片圆形化的控件，使用了 BitmapShader 而不是创建原始位图的副本、clipPath 或 setXfermode 剪辑位图。它的用法非常简单，基本和 ImageView 是完全一样的。

#### 2.12 下拉刷新 SwipeRefreshLayout

SwipeRefreshLayout 是用于实现下拉刷新功能的核心类，它是由 support-v4 库提供的。只要把想要实现下拉刷新功能的控件放置到 SwipeRefreshLayout 中，就可以迅速让这个控件支持下拉刷新。SwipeRefreshLayout 常常和 RecyclerView 一起使用。

### 3. 软件功能

#### 3.1 整体功能图

项目主要功能结构图☟

![项目主要功能结构图](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/%E9%A1%B9%E7%9B%AE%E4%B8%BB%E8%A6%81%E5%8A%9F%E8%83%BD%E7%BB%93%E6%9E%84%E5%9B%BE.png)

系统事务处理流程图☟

![系统事务处理流程图](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/%E7%B3%BB%E7%BB%9F%E4%BA%8B%E5%8A%A1%E5%A4%84%E7%90%86%E6%B5%81%E7%A8%8B%E5%9B%BE.png)

#### 3.2 登录模块和注册模块功能点描述

| 序号 | 功能           | 描述                                                         |
| ---- | -------------- | ------------------------------------------------------------ |
| 1    | 用户名密码验证 | 用户点击【登录】按钮后，会将输入的用户名和密码作为查找条件，从数据库中的寻找对应的用户 id，如果找得到则登录成功，否则登录失败。 |
| 2    | 输入非空验证   | 确保用户输入的用户名、密码或邮箱不为空。                     |
| 3    | 显示/隐藏密码  | 用户点击密码输入框末端的【眼睛】图标，会显示/隐藏密码文本。  |
| 4    | 自动登录       | 用户勾选【自动登录】后， 在下一次进入应用时将直接进入主页。  |
| 5    | 记住密码       | 用户勾选【记住密码】后，下一次进入登录页面时将不用再次输入用户名和密码。 |
| 6    | 多次输入密码   | 在用户注册或修改密码时，要求用户输入两次密码并确保一致。     |
| 7    | 忘记密码       | 用户可以通过注册时的邮箱设置新的密码。                       |
| 8    | 邮箱格式检查   | 在用户输入邮箱信息时，判断邮箱格式是否符合规范。不符合则提示用户“邮箱格式错误”。 |

#### 3.3 置顶、添加、列表页面功能描述

| 序号 | 功能               | 描述                                                         |
| ---- | ------------------ | ------------------------------------------------------------ |
| 1    | 显示问候语         | 用户进入首页后，会在上方看到一句亲切的、饱含激励与赞许的问候语。 |
| 2    | 显示当日时间       | 同时还会显示当日的日期，并每秒刷新现在的时间，提示用户岁月如流、白驹过隙。 |
| 3    | 编辑 note          | 用户在“置顶”或“全部”页面可以点击 note 项右下角的菜单按钮，点击【编辑】即可在自定义 Dialog 中对当前 note 进行标题和内容的修改。 |
| 4    | 置顶/取消置顶 note | 点击【置顶】或【取消置顶】按钮可以将当前 note 添加到“置顶”页面或从“置顶”页面删除。 |
| 5    | 删除 note          | 点击【删除】按钮可以将当前 note 删除。                       |
| 6    | 选择分类           | 用户在“添加”页面可以选择不同的 note 类型，同时上方的页面也会根据 note 类型而变化。 |
| 7    | 清空输入           | 点击【清空】按钮可以清除当前页面输入的所有内容。             |
| 8    | 保存 note          | 点击【保存】按钮可以保存输入的内容，在“全部”页面可以看到新增的 note。 |

#### 3.4 抽屉模块功能描述

| 序号 | 功能     | 描述                                                         |
| ---- | -------- | ------------------------------------------------------------ |
| 1    | 显示信息 | 在抽屉上方可以显示当前登录用户的头像、用户名和邮箱信息。     |
| 2    | 修改信息 | 点击抽屉中菜单项的【修改信息】按钮即可在自定义 Dialog 中对自己的信息进行修改。 |
| 3    | 注销账户 | 点击【注销账户】后会弹出自定义 Dialog，提示用户是否要注销自己的账户，避免误操作。如果账户注销，则自动退出登录，重新回到登录界面。 |
| 4    | 退出登录 | 点击【退出登录】后，自动登录按钮会被自动取消勾选，用户需要重新登录才能访问主页。 |
| 5    | 下载源码 | 点击【下载源码】后可以进入 App 的 Github 仓库网页，查看 App 的所有源码和提交信息。 |

#### 3.5 工具栏模块功能描述

| 序号 | 功能       | 描述                                                         |
| ---- | ---------- | ------------------------------------------------------------ |
| 1    | 打开抽屉   | 点击工具栏左上角的图标可以打开抽屉。用户也可以在界面左侧向右滑动打开抽屉。 |
| 2    | 按标题查找 | 点击工具栏右侧第一个按钮可以在自定义 Dialog 中输入想要查找的 note 的标题。点击确定后需要下拉刷新，即可看到查找结果。 |
| 3    | 按内容查找 | 点击第二个按钮可以在自定义 Dialog 中输入想要查找的 note 的内容。点击确定后需要下拉刷新，即可看到查找结果。 |
| 4    | 按分类查找 | 点击第三个按钮可以选择想要查找的 note 的类型。点击确定后需要下拉刷新，即可看到查找结果。 |

### 4. 运行截图

![](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/img_1.png)

![](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/img_2.png)

![](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/img_3.png)

![](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/img_4.png)

![](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/img_5.png)

![](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/img_6.png)

![](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/img_7.png)

![](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/img_8.png)

![](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/img_9.png)

![](https://gitee.com/horsenliu/SparrowNotes/raw/master/images/img_10.png)