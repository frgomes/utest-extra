Extra functionality to utest.

Features
--------

Now you can mark blocks as ignored and pending, as you would do in ScalaTest:

```scala
"This is an ignored test case" - ignored {
   // this is the test code
   assert(false)
}

"This test case is ignored when running by Jenkins" - ignoredif(jenkins) {
   // this test has requirements which Jenkins is not going to fulfill, for whatever reason
   assert(false)
}
```
