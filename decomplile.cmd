REM decompile.cmd scr1 src2 dst:
java -cp "C:\Users\Paul\AppData\Local\JetBrains\Toolbox\apps\IDEA-C\ch-0\201.7223.91\plugins\java-decompiler\lib\java-decompiler.jar" org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler -hdc=0 -dgs=1 -rsy=1 -rbr=1 -lit=1 -nls=1 -mpm=60 %*
