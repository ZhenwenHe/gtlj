# gtl-java
root@namenode1:~# cd /gtl
//root@namenode1:/gtl# svn co https://112.74.79.128/svn/gtl/trunk/gtl   [only run once]
root@namenode1:/gtl# cd gtl
root@namenode1:svn update
root@namenode1: cp -rf leastSigBits/dat ~/gtl/leastSigBits/dat
root@namenode1:/gtl/gtl# mvn package -Dmaven.test.skip=true 