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

SharedPreferences是使用键值对的方式来存储数据的。当保存一条数据的时候，需要给这条数据提供一个对应的键，这样在读取数据的时候就可以通过这个键把相应的值取出来。而且SharedPreferences还支持多种不同的数据类型存储。

#### 2.3 SQLite + OrmLite

SQLite是一款轻量级的关系型数据库，它的运算速度非常快，占用资源很少，通常只需要几百KB的内存就足够了，因而特别适合在移动设备上使用。SQLite不仅支持标准的SQL语法，还遵循了数据库的ACID事务，所以只要以前是用过其他的关系型数据库，就可以很快地上手SQLite。

ORM（全称Object Relation Mapping）叫做对象关系映射，是一种程序设计技术，用于实现面向对象编程语言中不同类型系统的数据之间的转换。它可以直接将Bean文件转换成数据库中的表，Bean中的属性就是表的列，它将繁琐的数据库操作封装成一个Dao类，使用Dao类和Bean可以直接对数据库进行操作，大多数的方法参数只有一个Bean对象。

#### 2.4 多线程编程 Handler + Message

Handler顾名思义也就是处理者的意思，它主要是用于发送和处理消息的。发送消息一般是使用Handler的sendMessage()方法，而发出的消息经过一系列的辗转处理后，最终会传递到Handler的handleMessage()方法中。

Message是在线程之间传递的消息，它可以在内部携带少量的信息，用于在不同线程之间交换数据。除了what字段，Message还可以使用arg1和arg2字段来携带一些整型数据，使用obj字段携带一个Object对象。

#### 2.5 Glide

Glide是一款快速高效的开源Android媒体管理和图片加载框架，它将媒体解码、内存和磁盘缓存以及资源池打包成一个简单易用的界面。

Glide支持抓取、解码和显示视频图片、图像和动态gif。Glide包括一个灵活的API，允许开发人员插入几乎任何网络堆栈。默认情况下，Glide使用了一个定制的基于HttpUrlConnection的堆栈，但也包含了可插入谷歌的Volley项目或Square的OkHttp库的实用程序库。

Glide的主要功能是尽可能平滑快速地滚动任何类型的图像列表，但它也适用于几乎任何需要获取、调整大小和显示远程图像的情况。

#### 2.6 RecyclerView控件

RecyclerView是一个增强版的ListView，不仅可以轻松实现和ListView同样的效果，还优化了ListView中存在的各种不足之处。同时RecyclerView支持横向滚动和瀑布流布局

#### 2.7 Fragment + ViewPager

Fragment是一种可以嵌入在Activity当中的UI片段，它能让程序更加合理和充分地利用大屏幕的空间，因而在平板上应用得非常广泛。它和Activity同样都能包含布局，同样都有自己的生命周期。甚至可以理解成一个迷你的Activity。

ViewPager是android扩展包v4包中的类，这个类可以让用户左右切换当前的view。ViewPager类直接继承了ViewGroup类，因此它一个容器类，可以添加其他的View类ViewPager类需要一个PagerAdapter适配器类给它提供数据（类似RecyclerView）。

ViewPager经常和Fragment一起使用，并且官方还提供了专门的FragmentPagerAdapter类供ViewPager使用。

#### 2.8 Toolbar控件

Toolbar 是 Android 5.0 推出的一个 Material Design 风格的导航控件 ,用来取代之前的 Actionbar 。与 Actionbar 相比，Toolbar 明显要灵活的多。它不像 Actionbar 一样，一定要固定在Activity的顶部，而是可以放到界面的任意位置。

#### 2.9 TextInputLayout控件

TextInputLayout主要是作为EditText的容器，从而为EditText生成一个浮动的Label，当用户点击EditText的时候，EditText中的hint字符串会自动移到EditText的左上角。

#### 2.10 滑动菜单 DrawerLayout + NavigationView

滑动菜单就是将一些菜单选项隐藏起来，而不是放在主屏幕上，然后可以通过滑动的方式将菜单显示出来。

DrawerLayout是一个布局，在布局中允许放入两个直接子控件，第一个子控件是主屏幕中显示的内容，第二个子控件是滑动菜单中显示的内容。

NavigationView是Design Support库中提供的一个控件，它不仅是严格按照Material Design的要求来进行设计的，而且还可以将滑动菜单页面的实现变得简单。

#### 2.11 CircleImageView控件

CircleImageView是一个用于将图片圆形化的控件，使用了BitmapShader而不是创建原始位图的副本、clipPath或setXfermode剪辑位图。它的用法非常简单，基本和ImageView是完全一样的。

#### 2.12 下拉刷新 SwipeRefreshLayout

SwipeRefreshLayout是用于实现下拉刷新功能的核心类，它是由support-v4库提供的。只要把想要实现下拉刷新功能的控件放置到SwipeRefreshLayout中，就可以迅速让这个控件支持下拉刷新。SwipeRefreshLayout常常和RecyclerView一起使用。
