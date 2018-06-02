@ rem ----- 信息 -----
@ rem @filename StartRedis.bat
@ rem @version 1.0
@ rem @author qye.zheng
@ rem @description 

@ rem @warning 为了防止中文环境乱码，保存文件的时候，应该保存为ANSI编码格式.
@ rem ################################################################################

:: 获取bat文件所在的磁盘驱动器，例如 C:
set currentDriver=%~dp0
set currentDriver=%currentDriver:~0,2%
:: 执行完其他批处理脚本，进入当前脚本所在的路径
%currentDriver%
cd %~dp0

:: 执行 home 脚本
call RedisConfig.bat

:: redis 所在的磁盘驱动
set redisDriver=%redisHome:~0,2%
:: 执行完其他批处理脚本，进入当前脚本所在的路径
%redisDriver%

:: 进入 redis home 目录
cd %redisHome%

@ rem 标题
@ title 启动Redis服务
@ rem ########## begin  ##########

@ rem 关闭显示命令，使所有命令执行前不显示
@ rem @ echo off
@ echo off
@ rem 打开命令显示 @ echo on

@ rem ----- 变量声明区


@ rem ----- 程序设计区

redis-server %redisConfPath%

@ rem pause

@ rem

@ rem
@ rem 输出提示信息

::
:: 1) 
:: 2)
:: 3)
:: 4)
:: 5)
:: 6)
:: 7)
:: 8)
:: 9)
:: 10)

:: 在程序的末尾，可以根据执行的结果(成功或失败) 给出提示信息，成功可以直接执行exit，而失败
:: 可以执行pause，然后可以通过控制台输出信息来调试、定位问题.
:: 可以在程序中设置一个成功或失败的标志-布尔值，来决定最后程序的执行流程.

@ rem echo
@ rem exit
@ rem ########## end of ##########

@ rem 注释说明: @ rem 注释内容  或者 :: 注释内容
@ rem rem 或两个冒号 后面 都可以写注释
