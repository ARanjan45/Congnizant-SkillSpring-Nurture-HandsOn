// EXERCISE 3: Builder Pattern-Computer

import java.util.*;

class Computer {
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
 
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
    }
 
    @Override
    public String toString() {
        return "Computer [CPU=" + cpu + ", RAM=" + ram +
               ", Storage=" + storage + ", GPU=" + gpu + "]";
    }
 
    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;
        private String gpu;
 
        public Builder cpu(String cpu) { this.cpu = cpu; return this; }
        public Builder ram(String ram) { this.ram = ram; return this; }
        public Builder storage(String storage) { this.storage = storage; return this; }
        public Builder gpu(String gpu) { this.gpu = gpu; return this; }
 
        public Computer build() { return new Computer(this); }
    }
}
 
class BuilderTest {
    public static void run() {
        System.out.println("\n=== Exercise 3: Builder Pattern ===");
        Computer gamingPC = new Computer.Builder()
            .cpu("Intel i9").ram("32GB").storage("2TB SSD").gpu("RTX 4090").build();
 
        Computer officePC = new Computer.Builder()
            .cpu("Intel i5").ram("8GB").storage("512GB SSD").build();
 
        System.out.println(gamingPC);
        System.out.println(officePC);
    }
}

class Main {
    public static void main(String[] args) {
        BuilderTest.run();
    }
}