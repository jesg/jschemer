package jschemer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import jschemer.lexer.Lexer;
import jschemer.lexer.SchemeLexer;
import jschemer.lexer.SchemeParser;

import org.junit.Test;

public class SchemerTest {

    @Test
    public void testCons() {
      
      List<Object> output = (List<Object>) eval("(cons 1 ())");
      assertEquals(1, output.size());
      assertEquals(1D, (Double)output.get(0), 0.001);
    }
    
    @Test
    public void testCar() {
      Double output = (Double) eval("(car (cons 1 ()))");
      assertEquals(1D, output, 0.001);
    }
    
    @Test
    public void testCdr() {
      List<Object> output = (List<Object>) eval("(cdr (cons 1 ()))");
      assertTrue(output.isEmpty());
    }
    
    @Test
    public void testeq() {
      Boolean output = (Boolean) eval("(eq 1 1)");
      assertTrue(output);
    }
    
    @Test
    public void testquote() {
      
      String output = (String) eval("(quote hi)");
      assertEquals("hi", output);
    }
    
    @Test
    public void positiveiftest() {
      
      Double output = (Double) eval("(if (eq 1 1) 1 0)");
      assertEquals(1D, output, 0.001);
    }
    
    @Test
    public void negativeiftest() {
      
      Double output = (Double) eval("(if (eq 1 2) 1 0)");
      assertNotEquals(1D, output, 0.001);
    }
    
    @Test
    public void testLambda() {
      
      Double output = (Double) eval("((lambda () 1))");
      assertEquals(1D, output, 0.001);
    }
    
    @Test
    public void testDefine() {
      
      Double output = (Double) eval("((lambda () (define x 1) x))");
      assertEquals(1D, output, 0.001);
    }
    
    @Test
    public void testSet() {
      
      Double output = (Double) eval("((lambda () (define y 2) (set! y 1) y))");
      assertEquals(1D, output, 0.001);
    }
    
    @Test
    public void testplus() {
      
      Double output = (Double) eval("(+ 2 2)");
      assertEquals(4D, output, 0.001);
    }
    
    @Test
    public void testminus() {
      
      Double output = (Double) eval("(- 2 4)");
      assertEquals(-2D, output, 0.001);
    }
    
    @Test
    public void testmult() {
      
      Double output = (Double) eval("(* 1 2 3)");
      assertEquals(6D, output, 0.001);
    }
    
    @Test
    public void testdiv() {
      
      Double output = (Double) eval("(/ 3 2)");
      assertEquals(3/2.0, output, 0.001);
    }
    
    @Test
    public void testincfunction() {
      Double output = (Double) eval("((lambda () (define inc (lambda (x) (+ x 1))) (inc 2)))");
      assertEquals(3D, output, 0.001);
    }
    
    private Object eval(String expr){
        Lexer lexer = new SchemeLexer(expr);
        SchemeParser parser = new SchemeParser(lexer);
        List<Object> list = parser.list();
        
        Environment env = new Environment();
        Repl.addGlobals(env);
        SchemerRuntime runtime = new SchemerRuntime(env);
        return runtime.eval(list);
    }
}
