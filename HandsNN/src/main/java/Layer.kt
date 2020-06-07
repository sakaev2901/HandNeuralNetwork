import java.io.Serializable

class Layer : Serializable {

    var size = 0;
    lateinit var neurons: DoubleArray
    lateinit var biases: DoubleArray
    lateinit var weights: Array<DoubleArray>

    constructor(size: Int, nextSize: Int) {
        this.size = size
        this.biases = DoubleArray(size){0.0}
        this.neurons = DoubleArray(size){0.0}
        this.weights = Array(size){DoubleArray(nextSize)}
    }
}