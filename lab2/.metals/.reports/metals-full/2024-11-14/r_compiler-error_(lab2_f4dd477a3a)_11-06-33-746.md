file:///C:/Users/artam/Desktop/FPlabs/FPlab/lab2/task2.scala
### java.nio.file.InvalidPathException: Illegal char <:> at index 3: jar:file:///C:/Users/artam/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.14/scala-library-2.13.14-sources.jar!/scala/util/Either.scala

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 2212
uri: file:///C:/Users/artam/Desktop/FPlabs/FPlab/lab2/task2.scala
text:
```scala
//2. 
// def goodEnoughPassword(password:String): Boolean
// Option

def hasNSymbols(a:String,set:String, n:Int): Boolean = a.filter(set.contains(_)).length() >= n

def goodEnoughPassword(password:String): Option[Boolean] = {
    val minLength = 8
    val lowercase = "abcdefghijklmnopqrstuvwxyz"
    val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "1234567890"
    val specials = "!@#$%^&*()[]{};:,./<>?|"
    val isValid = password.length() >= minLength &&
                  hasNSymbols(password,lowercase,1) &&
                  hasNSymbols(password,uppercase,1) &&
                  hasNSymbols(password,numbers,1) &&
                  hasNSymbols(password,specials,1)
    if (isValid) Some(true) else None
}

//3.
//def goodEnoughPassword(password:String):Either[Boolean, String]
//Try

def goodEnoughPassword2(password: String): Either[Boolean, String] = {
    val minLength = 8
    val lowercase = "abcdefghijklmnopqrstuvwxyz"
    val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "1234567890"
    val specials = "!@#$%^&*()[]{};:,./<>?|"

    (password.length >= minLength, 
    hasNSymbols(password, lowercase, 1), 
    hasNSymbols(password, uppercase, 1), 
    hasNSymbols(password, numbers, 1), 
    hasNSymbols(password, specials, 1)) match {

    case (true, true, true, true, true) => Left(true) 
    case _ if password.length < minLength => Right(s"Длина пароля должна быть не менее $minLength символов")
    case _ if !hasNSymbols(password, lowercase, 1) => Right("Пароль должен содержать хотя бы одну строчную букву")
    case _ if !hasNSymbols(password, uppercase, 1) => Right("Пароль должен содержать хотя бы одну заглавную букву")
    case _ if !hasNSymbols(password, numbers, 1) => Right("Пароль должен содержать хотя бы одну цифру")
    case _ if !hasNSymbols(password, specials, 1) => Right("Пароль должен содержать хотя бы один специальный символ")
  }
}

def readPassword():Future[String] = {
    Future {
        var validPassword = false
        var password = ""

        while (!validPassword) {
            password = readLine("Please enter a password: ")

            goodEnoughPassword2(password) m@@
        }
    }
}

@main def runApp = {
    val password1 = "StrongPass123!"
    val password2 = "weak"

    println(goodEnoughPassword(password1)) // Some(true)
    println(goodEnoughPassword(password2)) // None
}
```



#### Error stacktrace:

```
java.base/sun.nio.fs.WindowsPathParser.normalize(WindowsPathParser.java:182)
	java.base/sun.nio.fs.WindowsPathParser.parse(WindowsPathParser.java:153)
	java.base/sun.nio.fs.WindowsPathParser.parse(WindowsPathParser.java:77)
	java.base/sun.nio.fs.WindowsPath.parse(WindowsPath.java:92)
	java.base/sun.nio.fs.WindowsFileSystem.getPath(WindowsFileSystem.java:232)
	java.base/java.nio.file.Path.of(Path.java:147)
	java.base/java.nio.file.Paths.get(Paths.java:69)
	scala.meta.io.AbsolutePath$.apply(AbsolutePath.scala:58)
	scala.meta.internal.metals.MetalsSymbolSearch.$anonfun$definitionSourceToplevels$2(MetalsSymbolSearch.scala:70)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.metals.MetalsSymbolSearch.definitionSourceToplevels(MetalsSymbolSearch.scala:69)
	dotty.tools.pc.completions.CaseKeywordCompletion$.dotty$tools$pc$completions$CaseKeywordCompletion$$$sortSubclasses(MatchCaseCompletions.scala:342)
	dotty.tools.pc.completions.CaseKeywordCompletion$.matchContribute(MatchCaseCompletions.scala:292)
	dotty.tools.pc.completions.Completions.advancedCompletions(Completions.scala:348)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:120)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:90)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:146)
```
#### Short summary: 

java.nio.file.InvalidPathException: Illegal char <:> at index 3: jar:file:///C:/Users/artam/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.14/scala-library-2.13.14-sources.jar!/scala/util/Either.scala