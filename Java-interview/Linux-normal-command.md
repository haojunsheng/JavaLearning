**1）grep、sed 以及 awk 命令**

解析：awk 命令如果可以掌握，是面试中的一个 加分点。sed是用来处理和编辑文件的。

- grep 更适合单纯的查找或匹配文本,grep [option] pattern file|dir
-  sed 更适合编辑匹配到的文本
-  awk 更适合格式化文本，对文本进行较复杂格式处理

awk 每次处理一行，处理的最小单位是字段，每个字段的命名方式为：$n，n 为字段号，从 1 开始，$0 表示一整行。

示例：取出最近五个登录用户的用户名和 IP。首先用 last -n 5 取出用最近五个登录用户的所有信息，可以看到用户名和 IP 分别在第 1 列和第 3 列，我们用 $1 和 $3 就能取出这两个字段，然后用 print 进行打印。

```
$ last -n 5
dmtsai pts/0 192.168.1.100 Tue Jul 14 17:32 still logged in
dmtsai pts/0 192.168.1.100 Thu Jul 9 23:36 - 02:58 (03:22)
dmtsai pts/0 192.168.1.100 Thu Jul 9 17:23 - 23:36 (06:12)
dmtsai pts/0 192.168.1.100 Thu Jul 9 08:02 - 08:17 (00:14)
dmtsai tty1 Fri May 29 11:55 - 12:11 (00:15)
$ last -n 5 | awk '{print $1 "\t" $3}'
```

可以根据字段的某些条件进行匹配，例如匹配字段小于某个值的那一行数据。

```
$ awk '条件类型 1 {动作 1} 条件类型 2 {动作 2} ...' filename
```

示例：/etc/passwd 文件第三个字段为 UID，对 UID 小于 10 的数据进行处理。

```
$ cat /etc/passwd | awk 'BEGIN {FS=":"} $3 < 10 {print $1 "\t " $3}'
root 0
bin 1
daemon 2
```

awk 变量：

| 变量名称 | 代表意义                     |
| -------- | ---------------------------- |
| NF       | 每一行拥有的字段总数         |
| NR       | 目前所处理的是第几行数据     |
| FS       | 目前的分隔字符，默认是空格键 |

示例：显示正在处理的行号以及每一行有多少字段

```
$ last -n 5 | awk '{print $1 "\t lines: " NR "\t columns: " NF}'
dmtsai lines: 1 columns: 10
dmtsai lines: 2 columns: 10
dmtsai lines: 3 columns: 10
dmtsai lines: 4 columns: 10
dmtsai lines: 5 columns: 9
```

**2）文件和目录：**

> pwd 显示当前目录

ls 显示当前目录下的文件和目录：

1. ls -F 可以区分文件和目录；
2. ls -a 可以把隐藏文件和普通文件一起显示出来；
3. ls -R 可以递归显示子目录中的文件和目录；
4. ls -l 显示长列表；
5. ls -l test 过滤器，查看某个特定文件信息。可以只查看 test 文件的信息。

**find**用于在文件树中查找文件，并作出相应的处理。

find pathname -options [-print -exec -ok ...]

chmod用于改变 linux 系统文件或目录的访问权限。rwx的数值分别为421

chown 将指定文件的拥有者改为指定的用户或组，用户可以是用户名或者用户 ID；组可以是组名或者组 ID；文件是以空格分开的要改变权限的文件列表，支持通配符。

**3）处理文件方面的命令有：touch、cp、 In、mv、rm、**          

**4）处理目录方面的命令：mkdir**

**5）查看文件内容：file、cat、more、less、tail、head**

**6）监测程序命令：ps、top**

ps 工具标识进程的5种状态码:

```
D 不可中断 uninterruptible sleep (usually IO)
R 运行 runnable (on run queue)
S 中断 sleeping
T 停止 traced or stopped
Z 僵死 a defunct (”zombie”) process
```

**命令参数：**

```
-A 显示所有进程
a 显示所有进程
-a 显示同一终端下所有进程
c 显示进程真实名称
e 显示环境变量
f 显示进程间的关系
r 显示当前终端运行的进程
-aux 显示所有包含其它使用的进程
```

eg. 找出进程名中包括 java 的所有进程：ps -ef | grep java

top 命令实时监测进程,显示当前系统正在执行的进程的相关信息，包括进程 ID、内存占用率、CPU 占用率等

**常用参数：**

```
-c 显示完整的进程命令
-s 保密模式
-p <进程号> 指定进程显示
-n <次数>循环显示次数
```

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

```
-c 建立新的压缩文件
-f 指定压缩文件
-r 添加文件到已经压缩文件包中
-u 添加改了和现有的文件到压缩包中
-x 从压缩包中抽取文件
-t 显示压缩文件中的内容
-z 支持gzip压缩
-j 支持bzip2压缩
-Z 支持compress解压文件
-v 显示操作过程
```

打包：tar -cvf log.tar 1.log,2.log

打包并压缩：tar -czvf log.tar.gz 1.log,2.log

解压：tar -xzvf log.tar.gz 1.log,2.log

**9）结束进程：kill PID 或者 kill all**

```shell
pwd: print work directory 打印当前目录 显示出当前工作目录的绝对路径
ps: process status(进程状态，类似于windows的任务管理器)
 
常用参数：－auxf
 
ps -auxf 显示进程状态
 
df: disk free 其功能是显示磁盘可用空间数目信息及空间结点信息。换句话说，就是报告在任何安装的设备或目录中，还剩多少自由的空间。
du: Disk usage
rpm：即RedHat Package Management，是RedHat的发明之一
rmdir：Remove Directory（删除目录）
rm：Remove（删除目录或文件）
cat: concatenate 连锁
cat file1file2>>file3 把文件1和文件2的内容联合起来放到file3中
insmod: install module,载入模块
ln -s : link -soft 创建一个软链接，相当于创建一个快捷方式
mkdir：Make Directory(创建目录)
touch: touch
man: Manual
su：Swith user(切换用户)
cd：Change directory
ls：List files
ps：Process Status
mkdir：Make directory
rmdir：Remove directory
mkfs: Make file system
fsck：File system check
uname: Unix name
lsmod: List modules
mv: Move file
rm: Remove file
cp: Copy file
ln: Link files
fg: Foreground
bg: Background
chown: Change owner
chgrp: Change group
chmod: Change mode
umount: Unmount
dd: 本来应根据其功能描述"Convert an copy"命名为"cc"，但"cc"已经被用以代表"CComplier"，所以命名为"dd"
tar：Tape archive （磁带档案）
ldd：List dynamic dependencies
insmod：Install module
rmmod：Remove module
lsmod：List module
文件结尾的"rc"（如.bashrc、.xinitrc等）：Resource configuration
Knnxxx /Snnxxx（位于rcx.d目录下）：K（Kill）；S(Service)；nn（执行顺序号）；xxx（服务标识）
.a（扩展名a）：Archive，static library
.so（扩展名so）：Shared object，dynamically linked library
.o（扩展名o）：Object file，complied result of C/C++ source file
RPM：Red hat package manager
dpkg：Debian package manager
apt：Advanced package tool（Debian或基于Debian的发行版中提供）
```

