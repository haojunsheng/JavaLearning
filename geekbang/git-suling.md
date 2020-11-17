

# git基础

## 3. git最小配置

```
git config --global user.name ‘your_name’
git config --global user.email ‘your_email@domain.com’
```

config的作用域：

```
# 本仓库有效
git config --local
# 对登录用户所有仓库有效
git config --global
# system对系统的所有⽤用户有效
git config --system
```

显示配置：

```
➜  java git:(master) ✗ git config --list --local   
core.repositoryformatversion=0
core.filemode=true
core.bare=false
core.logallrefupdates=true
core.ignorecase=true
core.precomposeunicode=true
remote.origin.url=git@github.com:haojunsheng/JavaDeveloper.git
remote.origin.fetch=+refs/heads/*:refs/remotes/origin/*
(END)
```

优先级：local>global>system

## 6. 文件重命名

```
git mv old new
```

