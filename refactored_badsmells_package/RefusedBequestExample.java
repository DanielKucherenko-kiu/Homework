package badsmells;

/*
 * Refactored smell: Refused Bequest
 *
 * What changed:
 * - Removed the misleading Bird.fly() contract from non-flying birds
 * - Pushed flying behavior down into a FlyingBird subtype
 *
 * Why better:
 * Penguins are no longer forced to inherit behavior they cannot support. The
 * hierarchy now matches real behavior and avoids broken substitution.
 */
public class RefusedBequestExample {

    static abstract class Bird {
    }

    static abstract class FlyingBird extends Bird {
        public abstract void fly();
    }

    static class Sparrow extends FlyingBird {

        @Override
        public void fly() {
            System.out.println("Sparrow is flying");
        }
    }

    static class Eagle extends FlyingBird {

        @Override
        public void fly() {
            System.out.println("Eagle is soaring");
        }
    }

    static class Penguin extends Bird {

        public void swim() {
            System.out.println("Penguin is swimming");
        }
    }

    public void clientCode() {
        FlyingBird sparrow = new Sparrow();
        FlyingBird eagle = new Eagle();
        Penguin penguin = new Penguin();

        sparrow.fly();
        eagle.fly();
        penguin.swim();
    }
}
