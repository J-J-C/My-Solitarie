package test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import resource.Card;
import resource.Card.Rank;
import resource.Card.Suit;

public class Check {


  public static void main(String[] args) throws Exception {
    System.out.println(numberOfTestMethod("test.TestGameModel"));
    int count = countMethodsWithAnnotation(Class.forName("test.TestGameModel"),
        org.junit.Test.class);
    System.out.println(count);

    // reflection practice
    Card card = new Card(Rank.ACE, Suit.DIAMONDS);
    System.out.println(card.toString());
    Field b = card.getClass().getDeclaredField("aSuit");
    b.setAccessible(true);
    b.set(card, Suit.CLUBS);

    System.out.println(card.toString());
    System.out.println(b.getName());
    System.out.println(b.get(card));


  }


  private static int numberOfTestMethod(String pName) throws ClassNotFoundException {

    Class<?> c = Class.forName(pName);
    int count = 0;
    for (Method m : c.getMethods()) {
      if (m.isAnnotationPresent(org.junit.Test.class)) {
        count++;
      }
    }
    return count;

  }

  public static int countMethodsWithAnnotation(Class<?> klass,
      Class<? extends Annotation> annotation) {
    int count = 0;
    for (Method m : klass.getMethods()) {
      if (m.isAnnotationPresent(annotation)) {
        count++;
      }
    }
    return count;
  }


}
