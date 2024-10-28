file:///C:/Users/artam/Desktop/FPlabs/FPlab/lab1/task2/hello2.scala
### dotty.tools.dotc.MissingCoreLibraryException: Could not find package scala from compiler core libraries.
Make sure the compiler core libraries are on the classpath.
   

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
uri: file:///C:/Users/artam/Desktop/FPlabs/FPlab/lab1/task2/hello2.scala
text:
```scala
def  printHelloSeveralTimes(n: Int):Unit = {
    for (i <- 0 until n) {
        val x = if (i%2 == 0) i else n-i
        println(s"hello $x")

    }
}

@main def runApp(): Unit = {
    printHelloSeveralTimes(10)
}



```



#### Error stacktrace:

```
dotty.tools.dotc.core.Denotations$.select$1(Denotations.scala:1311)
	dotty.tools.dotc.core.Denotations$.recurSimple$1(Denotations.scala:1339)
	dotty.tools.dotc.core.Denotations$.recur$1(Denotations.scala:1341)
	dotty.tools.dotc.core.Denotations$.staticRef(Denotations.scala:1345)
	dotty.tools.dotc.core.Symbols$.requiredPackage(Symbols.scala:901)
	dotty.tools.dotc.core.Definitions.ScalaPackageVal(Definitions.scala:213)
	dotty.tools.dotc.core.Definitions.ScalaPackageClass(Definitions.scala:216)
	dotty.tools.dotc.core.Definitions.AnyClass(Definitions.scala:276)
	dotty.tools.dotc.core.Definitions.syntheticScalaClasses(Definitions.scala:2109)
	dotty.tools.dotc.core.Definitions.syntheticCoreClasses(Definitions.scala:2123)
	dotty.tools.dotc.core.Definitions.init(Definitions.scala:2139)
	dotty.tools.dotc.core.Contexts$ContextBase.initialize(Contexts.scala:900)
	dotty.tools.dotc.core.Contexts$Context.initialize(Contexts.scala:523)
	dotty.tools.dotc.interactive.InteractiveDriver.<init>(InteractiveDriver.scala:41)
	dotty.tools.pc.MetalsDriver.<init>(MetalsDriver.scala:30)
	dotty.tools.pc.ScalaPresentationCompiler.newDriver(ScalaPresentationCompiler.scala:99)
```
#### Short summary: 

dotty.tools.dotc.MissingCoreLibraryException: Could not find package scala from compiler core libraries.
Make sure the compiler core libraries are on the classpath.
   