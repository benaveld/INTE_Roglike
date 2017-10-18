package rougelikeLibrary;

/**
 * Wrapper class for mappable types
 */
class MappableTypeWrapper {
    Class classType;
    Class [] parameterTypes;
    Object [] parameterValues;
    IO io;
    int minQuantity;
    int maxQuantity;
    int probability;
    int currentQuantity;


    MappableTypeWrapper(Class classType, Class [] parameterTypes, Object [] parameterValues, int minQuantity, int maxQuantity, int probability) {
        this.classType = classType;
        this.parameterTypes = parameterTypes;
        this.parameterValues = parameterValues;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
        this.probability = probability;
    }


    MappableTypeWrapper(Class classType, Class [] parameterTypes, Object [] parameterValues, IO io, int minQuantity, int maxQuantity, int probability) {
        this.classType = classType;
        this.parameterTypes = parameterTypes;
        this.parameterValues = parameterValues;
        this.io = io;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
        this.probability = probability;
    }


    void addQuantity() {
        currentQuantity++;
    }

    boolean isMinQuantity() {
        return currentQuantity >= minQuantity;
    }

    boolean isMaxQuantity() {
        return currentQuantity >= maxQuantity;
    }
}
