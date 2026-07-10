# 🏛️ Topic 07: The Four Pillars of OOP

Object-Oriented Programming (OOP) has **four superpowers** that make it extremely clean, reusable, and secure. Let's learn these pillars using fun, real-world stories!

---

## 🔒 Pillar 1: Encapsulation (Guarding Secrets)

### 🏠 The Big Picture & Real-Life Example: The Diary with a Lock 🔑
Imagine you have a secret diary. You don't want anyone to grab it and write silly things inside. So, you lock the diary and put a code on it. If someone wants to read a page or write something, they have to ask *you* first. You check if their request is valid, and then you open the lock.

In Java, we do this by hiding variables and only allowing access through safety doors:
1. Make variables **`private`** (so no one can change them directly).
2. Create **Getter** and **Setter** methods to read and write values safely.

### 🔬 Access Modifiers Table
Java has security access levels to guard your files:

| Modifier | Class | Package | Subclass | World | Analogy |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **`private`** | Yes 🟢 | No 🔴 | No 🔴 | No 🔴 | Secret thoughts in your head |
| **`default`** (no keyword) | Yes 🟢 | Yes 🟢 | No 🔴 | No 🔴 | Toys shared inside your house |
| **`protected`** | Yes 🟢 | Yes 🟢 | Yes 🟢 | No 🔴 | Family heirlooms passed to children |
| **`public`** | Yes 🟢 | Yes 🟢 | Yes 🟢 | Yes 🟢 | A public park bench |

---

## 👪 Pillar 2: Inheritance (Family Traits)

### 🏠 The Big Picture & Real-Life Example: The Parent's Bike 🚲
Imagine a parent has a cool blue bicycle. When the child grows up, the parent gives the bicycle to them. The child automatically gets the bike with two wheels, pedals, and a seat. But the child decides to paint it **red** and add a shiny bell. The child got the base bike for free (inherited it) but customized it!

In Java:
* The parent class is the **Superclass** (or Base class).
* The child class is the **Subclass** (or Derived class).
* We use the **`extends`** keyword to inherit.
* We use the **`super`** keyword to call the parent's constructor or methods.

> [!WARNING]
> **No Multiple Inheritance for Classes!**
> In Java, a child class can only have **one** direct parent class. You cannot inherit from two different classes at the same time (to avoid confusing duplicate rules!).

---

## 🎭 Pillar 3: Polymorphism (The Shape-Shifter)

### 🏠 The Big Picture & Real-Life Example: The Magic "Make Sound" Command 🗣️
Imagine you have a list of animal toys: a dog, a cat, and a cow. You press a magic button that says "Make Sound!". 
* The Dog goes *"Woof!"*
* The Cat goes *"Meow!"*
* The Cow goes *"Moo!"*

Even though you pressed the **same** button name, each toy shape-shifted the action into its own unique sound. That is **Polymorphism** (which means "many shapes")!

There are two ways to do this in Java:

### 1. Compile-Time Polymorphism (Method Overloading)
Creating methods with the **same name** but **different parameters** (arguments) inside the *same class*.
* *Analogy*: A magician says "Abracadabra!" loudly if there is a big crowd, or whispers it if there is only one person. Same spell, different ways of speaking!

### 2. Runtime Polymorphism (Method Overriding)
A child class rewrites a parent class method to do its own custom action using the `@Override` tag.
* *Analogy*: The parent's bike goes ding-ding, but the child overrides the sound to make it go beep-beep!
* **Upcasting**: Creating a parent variable pointing to a child object (e.g., `Animal myPet = new Dog();`).
* **Downcasting**: Turning a generic parent reference back into a specific child object (using the `instanceof` check to be safe!).

---

## 🖼️ Pillar 4: Abstraction (Hiding the Engine)

### 🏠 The Big Picture & Real-Life Example: The Game Controller 🎮
Imagine you are playing a video game. You press the **"Jump"** button on your controller, and Mario jumps. You don't know (and don't care!) how the electrical wires, chips, and code inside the console make the jump happen. You only care about the button! The messy stuff is hidden, and only the controls are shown to you.

In Java, we achieve this using:
1. **Abstract Classes**: A half-written blueprint. It can have implemented methods, but also has empty abstract methods that child classes *must* fill in.
2. **Interfaces**: A strict rules sheet (or contract). It lists empty methods. Any class that `implements` the interface promises to follow and write those methods.

### ⚖️ Abstract Class vs. Interface

| Feature | Abstract Class | Interface |
| :--- | :--- | :--- |
| **Purity** | Partial blueprint (can have variables and full methods) | Pure specification (mostly empty methods) |
| **Inheritance** | A class can only `extend` **one** class | A class can `implement` **many** interfaces |
| **Variables** | Can have normal, changing variables | Only constants (`public static final`) |
| **Modern Java (8+)** | Same as before | Can now have `default`, `static`, and `private` helper methods! |

---

## 📖 Key Definitions

* **Encapsulation**: Guarding sensitive data by making variables private and providing access only through public getters and setters.
* **Inheritance**: A mechanism that lets a child class borrow the fields and methods of a parent class.
* **Polymorphism**: The ability of a method or object to behave differently in different situations (like overriding or overloading).
* **Abstraction**: Hiding complex internal execution details and exposing only the essential features to the user.
* **Abstract Class**: A class that cannot be instantiated and is meant to be extended, serving as a partial blueprint.
* **Interface**: A formal contract specifying a set of method signatures that implementing classes must define.

---

## 💻 Code Sandbox: The OOP Kingdom

Copy, play, and run this code:

```java
// --- 1. ABSTRACTION & INTERFACE ---
interface Flyable {
    void fly(); // No body, just a rule!
}

abstract class Animal {
    // 2. ENCAPSULATION
    private String name; // Private secret variable!

    public Animal(String name) {
        this.name = name;
    }

    // Getter (Safe door to read name)
    public String getName() {
        return name;
    }

    // Abstract method (Children MUST write this!)
    public abstract void makeNoise();
}

// --- 3. INHERITANCE ---
class Bird extends Animal implements Flyable {
    
    public Bird(String name) {
        super(name); // Call parent constructor
    }

    // 4. POLYMORPHISM (Method Overriding)
    @Override
    public void makeNoise() {
        System.out.println(getName() + " says: Tweet! Tweet!");
    }

    @Override
    public void fly() {
        System.out.println(getName() + " is flapping its wings and flying!");
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeNoise() {
        System.out.println(getName() + " says: Woof! Woof!");
    }
}

public class PillarsDemo {
    public static void main(String[] args) {
        // Polymorphism: Using parent references to point to different child objects
        Animal pet1 = new Bird("Tweety");
        Animal pet2 = new Dog("Buddy");

        pet1.makeNoise(); // Tweety says: Tweet! Tweet!
        pet2.makeNoise(); // Buddy says: Woof! Woof!

        // Let's check if pet1 is a Bird, and if so, fly!
        if (pet1 instanceof Flyable) {
            Flyable flyingPet = (Flyable) pet1; // Downcasting
            flyingPet.fly(); // Tweety is flapping its wings and flying!
        }
    }
}
```

---

> [!IMPORTANT]
> * Use **`private`** variables by default to protect your data, and write getters/setters.
> * Use the `@Override` annotation when overriding methods to prevent spelling mistakes!
> * An **Interface** represents a capability (e.g., `Flyable`, `Runnable`, `Serializable`).
> * You cannot create an object directly from an Abstract Class or an Interface (e.g., `new Animal()` is illegal!).

---

## ❓ Interview Questions (Q1 - Q50)

### 🟢 Basic Questions (Q1 - Q20)
1. **What are the four main pillars of OOP?**
   * *Answer*: Encapsulation, Inheritance, Polymorphism, and Abstraction.
2. **What is Encapsulation?**
   * *Answer*: The practice of bundling data (variables) and behaviors (methods) together into a single unit (class) and restricting direct access to the data.
3. **What is Inheritance?**
   * *Answer*: A mechanism where one class (subclass) inherits the state and behavior of another class (superclass).
4. **What is Polymorphism?**
   * *Answer*: The ability of a single interface or method to perform different actions based on the object type calling it (many forms).
5. **What is Abstraction?**
   * *Answer*: The process of hiding complex internal details and exposing only the essential features of an object.
6. **What is the difference between `public` and `private` modifiers?**
   * *Answer*: `public` members are accessible from any class in the program; `private` members are accessible only within the declaring class.
7. **What is the `protected` modifier?**
   * *Answer*: It allows access within the same package and by subclasses in other packages.
8. **What does the `extends` keyword do?**
   * *Answer*: It is used to declare that a class inherits from a parent superclass.
9. **What does the `implements` keyword do?**
   * *Answer*: It is used by a class to promise to implement all methods declared in a specific interface.
10. **What is the difference between method overloading and method overriding?**
    * *Answer*: 
      * Overloading happens in the same class (same name, different arguments, compile-time).
      * Overriding happens in a subclass (same name, same arguments, runtime).
11. **What is the `super` keyword used for?**
    * *Answer*: To access fields, invoke methods, or call constructors of the parent superclass.
12. **Can you instantiate an abstract class?**
    * *Answer*: No, abstract classes cannot be created directly using the `new` keyword.
13. **Can you instantiate an interface?**
    * *Answer*: No, interfaces cannot be instantiated directly.
14. **What is the default modifier of a class member if no access modifier is written?**
    * *Answer*: Package-private (often called `default` access), which restricts access to classes within the same package.
15. **Can a class inherit from multiple classes in Java?**
    * *Answer*: No, Java does not support multiple inheritance of classes (a class can only extend one parent class).
16. **Can a class implement multiple interfaces?**
    * *Answer*: Yes, a class can implement any number of interfaces.
17. **What are getters and setters?**
    * *Answer*: Public methods used to retrieve (get) and modify (set) the value of a private instance variable safely.
18. **What is the `@Override` annotation?**
    * *Answer*: An optional metadata tag that instructs the compiler to verify that the method actually overrides a parent class method, preventing typos.
19. **What is the difference between compile-time and runtime polymorphism?**
    * *Answer*: Compile-time is method overloading; runtime is method overriding.
20. **Can we override a `static` method?**
    * *Answer*: No, static methods are bound to the class at compile-time; overriding only applies to instance methods at runtime.

### 🟡 Intermediate Questions (Q21 - Q40)
21. **What is the difference between an Abstract Class and an Interface?**
   * *Answer*: 
     * Abstract classes can have state (instance variables) and constructors; interfaces cannot have instance state (only constants).
     * A class can extend only one abstract class but implement multiple interfaces.
22. **What is the "Diamond Problem" in multiple inheritance and how does Java avoid it?**
   * *Answer*: An ambiguity where a subclass inherits different implementations of the same method from two parents. Java avoids this by forbidding multiple inheritance of classes.
23. **How does Java handle method resolution conflicts if a class implements two interfaces with the same default method signature?**
   * *Answer*: The compiler forces the implementing class to explicitly override the conflicting method and specify which interface method to run (using `InterfaceName.super.methodName()`).
24. **What is Upcasting? Is it safe?**
   * *Answer*: Casting a child object to a parent reference type (e.g., `Animal a = new Dog();`). Yes, it is always safe and handled automatically.
25. **What is Downcasting? What risk is involved?**
   * *Answer*: Casting a parent reference type back to a child object type (e.g., `Dog d = (Dog) a;`). The risk is a `ClassCastException` at runtime if the object is not actually of the child type.
26. **What is the purpose of the `instanceof` operator?**
   * *Answer*: To check if an object is of a specific class type or implements an interface before performing downcasting.
27. **What is the difference between static binding and dynamic binding?**
   * *Answer*: 
     * Static binding occurs at compile-time (overloaded methods, static/private/final methods).
     * Dynamic binding occurs at runtime based on the actual object type (overridden instance methods).
28. **Can a `final` class be inherited?**
   * *Answer*: No, a class declared `final` cannot be extended by any other class (e.g., `String` is a final class).
29. **Can a `final` method be overridden?**
   * *Answer*: No, a method marked `final` cannot be overridden by subclasses.
30. **What is a default method in an interface?**
   * *Answer*: A method introduced in Java 8 that contains a body (`default void run() { ... }`), allowing interfaces to add new methods without breaking existing implementing classes.
31. **Can interfaces contain static methods?**
   * *Answer*: Yes, since Java 8, interfaces can define static methods containing implementations, which belong to the interface itself.
32. **Can interfaces contain private methods?**
   * *Answer*: Yes, since Java 9, interfaces can contain private methods to act as helper code blocks shared among default interface methods.
33. **What is the difference between overriding a method and hiding a method?**
   * *Answer*: Overriding applies to instance methods (resolved dynamically at runtime); method hiding applies to static methods (resolved statically at compile-time based on the reference type).
34. **Does an abstract class require abstract methods?**
   * *Answer*: No, a class can be declared `abstract` even if it contains only fully implemented concrete methods.
35. **What happens if a subclass does not implement all abstract methods of its abstract parent?**
   * *Answer*: The subclass must also be declared `abstract`, delegating implementations to its own child classes.
36. **Can we declare an abstract class as `final`?**
   * *Answer*: No, `abstract` and `final` are contradictory. An abstract class *must* be inherited to be useful, while a final class *cannot* be inherited.
37. **Can we declare interface methods as `protected`?**
   * *Answer*: No, all abstract interface methods are implicitly `public` (explicitly declaring them protected results in a compile-time error).
   * *Note*: They can be private since Java 9.
38. **What is the difference between inheritance and composition?**
   * *Answer*: Inheritance models an "IS-A" relationship (e.g., Dog is an Animal); composition models a "HAS-A" relationship by including an object as a field (e.g., Car has an Engine).
39. **Why is composition often preferred over inheritance?**
   * *Answer*: Composition is more flexible because it decouples dependencies, avoids superclass changes breaking subclasses (fragile base class problem), and allows behavior changes at runtime.
40. **What is tight coupling vs. loose coupling?**
    * *Answer*: Tight coupling occurs when classes depend directly on concrete classes (hard to modify); loose coupling occurs when classes interact via abstract interfaces, allowing independent changes.

### 🔴 Advanced Questions (Q41 - Q50)
41. **Explain how the JVM executes method overrides using Virtual Method Tables (vtables).**
   * *Answer*: For instance methods, the JVM constructs a lookup table (vtable) for each class containing method names and their memory addresses. When `invokevirtual` executes, it looks up the method offset in the vtable of the actual runtime object, enabling dynamic dispatch.
42. **How does `invokeinterface` differ from `invokevirtual` in bytecode execution performance?**
   * *Answer*: `invokevirtual` resolves methods using fixed offsets in the vtable. `invokeinterface` is slightly slower because a class can implement multiple interfaces, meaning interface methods cannot occupy the same offset index, requiring a dynamic table search (itable) at runtime.
43. **What are Covariant Return Types in method overriding?**
   * *Answer*: A feature introduced in Java 5 where an overriding method in a subclass can return a subtype of the return type declared in the parent method (e.g., parent returns `Animal`, child overrides and returns `Dog`).
44. **What is a Marker Interface? Give examples.**
   * *Answer*: An interface that contains no methods or fields (e.g., `java.lang.Cloneable`, `java.io.Serializable`). It serves as a metadata tag to inform the compiler or JVM that implementing objects have specific capabilities.
45. **Why does Java reject multiple inheritance of classes but allow multiple inheritance of interfaces containing default methods?**
   * *Answer*: Interfaces do not contain state (fields). When default methods conflict, Java forces compile-time resolution. Multiple inheritance of classes is blocked because subclass instance layout cannot easily incorporate conflicting state offsets from multiple parent classes.
46. **What is the "Fragile Base Class" problem in inheritance?**
   * *Answer*: A design issue where modification of the superclass (e.g., adding a method call inside an existing method) unexpectedly alters the behavior or breaks the invariants of subclasses that override those methods.
47. **Can a subclass relax the access visibility of an overridden method?**
   * *Answer*: Yes, an overriding method can increase visibility (e.g., overriding a `protected` method and declaring it `public`). It cannot restrict visibility (e.g., overriding a `public` method and declaring it `private`) because it violates Liskov Substitution Principle.
48. **Explain the Liskov Substitution Principle (LSP).**
   * *Answer*: A software design principle stating that objects of a superclass must be replaceable with objects of subclasses without affecting the correctness of the program.
49. **How does the JVM compiler optimize polymorphism using devirtualization?**
   * *Answer*: If the JIT compiler analyzes the class hierarchy and finds that a polymorphic method call site is only ever executed by one concrete receiver class type (monomorphic), it optimizes by bypassing vtable lookup and inlines the target method directly.
50. **What is runtime type information (RTTI) and how does Java maintain it?**
    * *Answer*: The capability to query object types at runtime. The JVM maintains RTTI by embedding a reference to class metadata (an internal Class object reference) inside the object header of every object instance in the Heap.

