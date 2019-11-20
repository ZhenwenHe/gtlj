本项目旨在实现一个基于微服务架构的多源异构大数据管理平台。具体见gtl-java/docs目录下的gtl2-introduction.docx文档。具备下列主要功能：
1）具备结构化数据管理功能，也就是能够管理关系数据库中的数据；并支持以服务的方式提供增删查改；
2）具备文件和目录管理功能，并支持以服务的方式方式提供增删查；
3）具备非结构化文本数据处理与管理功能，包括文本抽取、文本索引、文本存储、文本搜索、中文语言处理等；
4）具备非结构化图形数据处理与管理功能，包括空间对象抽取，空间索引、空间数据存储和空间查询与分析等；
5）具备非结构化栅格数据处理与管理功能，包括栅格数据划分、栅格数据索引、栅格数据存储、栅格数据查询与分析等；
6）可以方便地为各种应用定制为符合行业规则的大数据管理系统；
7）可以统一的服务接口，为其他系统提供数据支撑；
8）有方便的用户管理界面。

    
开发过程中，建议的目录设置为：
代码放在 ~/git/gtl-java中，
测试数据放在 ~/git/data中。
~表示当前用户主目录。

在当前用户目录下,创建git目录
ZhenwendeMacBook-Pro:~ zhenwenhe$ mkdir git
ZhenwendeMacBook-Pro:~ zhenwenhe$ cd git
ZhenwendeMacBook-Pro:git zhenwenhe$ git clone https://github.com/ZhenwenHe/gtl-java.git

Series说明：
对于~/git/gtl-java/Series 需要将UCRArchive_2018整个目录放置在~/git/data下。此外，~/git/data/log下必须有一个series.db文件，里面记录了运行参数。可以将~/git/gtl-java/Series/series.db文件拷贝过去。生成的测试结果放在~/git/data/output/hax_nn.xls中。



如果不采用上述默认设置，则需要采用，
Config.setDataDirectory()设置数据根目录。

在飞测试代码中，获取数据目录请采用Config.getDataDirectory()。
在编写所有的测试代码过程中，
请使用Config.getTestInputDirectory()
函数获取测试数据的根目录。
输出结果目录存放位置请采用
Config.getTestOutputDirectory()
函数获取。


编译，进入gtl-java目录，采用mvn命令编译
root@namenode1:/gtl-java# mvn package -Dmaven.test.skip=true 
