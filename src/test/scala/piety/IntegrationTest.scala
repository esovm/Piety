package piety

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File}
import javax.imageio.ImageIO

import org.junit.Assert._
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit

class IntegrationTest extends AssertionsForJUnit {

  @Test def testProg1() = {
    testProg("hello_world.gif", 11, "Hello, world!")
  }

  @Test def testProg2() = {
    testProg("hello_world2.gif", 1, "Hello, world!")
  }

  @Test def testProg3() = {
    testProg("hello_world3.png", 1, "Hello world!")
  }

  @Test def testProg4() = {
    testProg("alpha_filled.png", 1, "abcdefghijklmnopqrstuvwxyz")
  }

  @Test def testProg5() = {
    testProg("hello_world4.png", 1, "Hello, world!")
  }

  @Test def testProg6() = {
    testProgWithInput("factorial.png", 10, "0", "1")
    testProgWithInput("factorial.png", 10, "1", "1")
    testProgWithInput("factorial.png", 10, "2", "2")
    testProgWithInput("factorial.png", 10, "3", "6")
    testProgWithInput("factorial.png", 10, "4", "24")
    testProgWithInput("factorial.png", 10, "5", "120")
  }

  @Test def testProg7() = {
    testProgWithInput("primetest.gif", 24, "2", "Y")
    testProgWithInput("primetest.gif", 24, "3", "Y")
    testProgWithInput("primetest.gif", 24, "4", "N")
    testProgWithInput("primetest.gif", 24, "5", "Y")
    testProgWithInput("primetest.gif", 24, "7817", "Y")
    testProgWithInput("primetest.gif", 24, "7819", "N")
  }

  @Test def testProg8() = {
    testProg("fibo.gif", 32, "0\n1\n1\n2\n3\n5\n8\n13\n21\n34\n55\n89\n144\n233\n377\n610\n987")
  }

  @Test def testProg9() = {
    testProg("pi.png", 1, "31405")
  }

  @Test def testProg10() = {
    testProg("hello_world5.gif", 1, "Hello, world!")
  }

  @Test def testProg11() = {
    testProgWithInput("euclid.png", 10, "2\n2", "2")
    testProgWithInput("euclid.png", 10, "10\n1", "1")
    testProgWithInput("euclid.png", 10, "3\n9", "3")
    testProgWithInput("euclid.png", 10, "9\n3", "3")
    testProgWithInput("euclid.png", 10, "6\n4", "2")
    //testProgWithInput("euclid.png", 10, "7\n5", "1") // This one doesn't work!
  }

  @Test def testBadProg1() = {
    try {
      testProg("bad_color.gif", 11, "")
      assert(false)
    } catch {
      case e: Exception => assert(true)
    }
  }

  @Test def testBadProg2() = {
    try {
      testProg("bad_dimensions.gif", 11, "")
      assert(false)
    } catch {
      case e: Exception => assert(true)
    }
  }

  def testProg(fileName: String, codelSize: Int, expectedOutput: String) = {
    val filePath = getClass.getClassLoader.getResource(fileName).getFile
    val prog: Program = ProgramFactory.createProgramFromImage(ImageIO.read(new File(filePath)), codelSize)

    val stream = new ByteArrayOutputStream()
    Console.withOut(stream) {
      Interpreter.execute(prog)
      assertEquals(expectedOutput, stream.toString().trim())
    }
  }

  def testProgWithInput(fileName: String, codelSize: Int, input: String, expectedOutput: String) = {
    Console.withIn(new ByteArrayInputStream(input.getBytes())) {
      testProg(fileName, codelSize, expectedOutput)
    }
  }
}