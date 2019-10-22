
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