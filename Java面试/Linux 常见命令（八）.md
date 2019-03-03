**1）grep、sed 以及 awk 命令**



解析：awk 命令如果可以掌握，是面试中的一个 加分点。

sed是用来处理和编辑文件的。

- grep 更适合单纯的查找或匹配文本
-  sed 更适合编辑匹配到的文本
-  awk 更适合格式化文本，对文本进行较复杂格式处理



**2）文件和目录：**

> pwd 显示当前目录

ls 显示当前目录下的文件和目录：

1. ls -F 可以区分文件和目录；
2. ls -a 可以把隐藏文件和普通文件一起显示出来；
3. ls -R 可以递归显示子目录中的文件和目录；
4. ls -l 显示长列表；
5. ls -l test 过滤器，查看某个特定文件信息。可以只查看 test 文件的信息。

**3）处理文件方面的命令有：touch、cp、 In、mv、rm、**          

**4）处理目录方面的命令：mkdir**

**5）查看文件内容：file、cat、more、less、tail、head**

**6）监测程序命令：ps、top**

eg. 找出进程名中包括 java 的所有进程：ps -ef | grep java

top 命令 实时监测进程

top 命令输出的第一部分：显示系统的概括。

1. 第一行显示了当前时间、系统的运行时间、登录的用户数和系统的平均负载（平均负载有 3 个值：最近 1min 5min 15min）；
2. 第二行显示了进程的概要信息，有多少进程处于运行、休眠、停止或者僵化状态；
3. 第三行是 CPU 的概要信息；
4. 第四行是系统内存的状态。

**7）ps 和 top 命令的区别：**

1. ps 看到的是命令执行瞬间的进程信息 , 而 top 可以持续的监视；
2. ps 只是查看进程 , 而 top 还可以监视系统性能 , 如平均负载 ,cpu 和内存的消耗；
3. 另外 top 还可以操作进程 , 如改变优先级 (命令 r) 和关闭进程 (命令 k)；
4. ps 主要是查看进程的，关注点在于查看需要查看的进程；
5. top 主要看 cpu, 内存使用情况，及占用资源最多的进程由高到低排序，关注点在于资源占用情况。

**8） 压缩数据**

1. tar -xvf 文件名；
2. tar -zxvf 文件名；
3. tar -cvzf 文件名。

**9）结束进程：kill PID 或者 kill all**



## 1.查找文件

`find / -name filename.txt` 根据名称查找/目录下的filename.txt文件。

`find . -name "*.xml"` **递归**查找所有的xml文件(./不递归)

`find . -name "*.xml" |xargs grep "hello world"` 递归查找所有文件内容中包含hello world的xml文件

`grep -H 'spring' *.xml` 查找所以有的包含spring的xml文件

`find ./ -size 0 | xargs rm -f &` 删除文件大小为零的文件

`ls -l | grep '.jar'` 查找当前目录中的所有jar文件

`grep 'test' d*` 显示所有以d开头的文件中包含test的行。

`grep 'test' aa bb cc` 显示在aa，bb，cc文件中匹配test的行。

`grep '[a-z]\{5\}' aa` 显示所有包含每个字符串至少有5个连续小写字符的字符串的行。

## 2.查看一个程序是否运行

`ps –ef|grep tomcat` 查看所有有关tomcat的进程

`ps -ef|grep --color java` 高亮要查询的关键字

## 3.终止线程

`kill -9 19979` 终止线程号位19979的进程

## 4.查看文件，包含隐藏文件

```
ls -al
```

## 5.当前工作目录

```
pwd
```

## 6.复制文件

`cp source dest` 复制文件

`cp -r sourceFolder targetFolder` 递归复制整个文件夹

`scp sourecFile romoteUserName@remoteIp:remoteAddr` 远程拷贝

## 7.创建目录

```
mkdir newfolder
```

## 8.删除目录

`rmdir deleteEmptyFolder` 删除空目录 `rm -rf deleteFile` 递归删除目录中所有内容

## 9.移动文件

```
mv /temp/movefile /targetFolder
```

## 10.重命令

```
mv oldNameFile newNameFile
```

## 11.切换用户

```
su -username
```

## 12.修改文件权限

`chmod 777 file.java` //file.java的权限-rwxrwxrwx，r表示读、w表示写、x表示可执行

## 13.压缩文件

```
tar -czf test.tar.gz /test1 /test2
```

## 14.列出压缩文件列表

```
tar -tzf test.tar.gz
```

## 15.解压文件

```
tar -xvzf test.tar.gz
```

## 16.查看文件头10行

```
head -n 10 example.txt
```

## 17.查看文件尾10行

```
tail -n 10 example.txt
```

## 18.查看日志类型文件

`tail -f exmaple.log` //这个命令会自动显示新增内容，屏幕只显示10行内容的（可设置）。

## 19.使用超级管理员身份执行命令

`sudo rm a.txt` 使用管理员身份删除文件

## 20.查看端口占用情况

`netstat -tln | grep 8080` 查看端口8080的使用情况

## 21.查看端口属于哪个程序

```
lsof -i :8080
```

## 22.查看进程

`ps aux|grep java` 查看java进程

`ps aux` 查看所有进程

## 23.以树状图列出目录的内容

```
tree a
```

ps:[Mac下使用tree命令](http://www.hollischuang.com/archives/546)

## 24. 文件下载

`wget http://file.tgz` [mac下安装wget命令](http://www.hollischuang.com/archives/548)

```
curl http://file.tgz
```

## 25. 网络检测

```
ping www.just-ping.com
```

## 26.远程登录

```
ssh userName@ip
```

## 27.打印信息

`echo $JAVA_HOME` 打印java home环境变量的值

## 28.java 常用命令

java javac [jps](http://www.hollischuang.com/archives/105) ,[jstat](http://www.hollischuang.com/archives/481) ,[jmap](http://www.hollischuang.com/archives/303), [jstack](http://www.hollischuang.com/archives/110)

## 29.其他命令

svn git maven

## 28.linux命令学习网站:

<http://explainshell.com/>

## 参考资料：

[Linux端口被占用的解决(Error: JBoss port is in use. Please check)](http://www.hollischuang.com/archives/239)

[linux 中强大且常用命令：find、grep](https://linux.cn/article-1672-1.html)

[Linux命令](http://blog.csdn.net/tianshijianbing1989/article/details/40780463)