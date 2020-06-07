import java.io.Serializable
import kotlin.random.Random

class NeuralNetwork : Serializable{
    var learningRate = 0.0
    lateinit var layers: Array<Layer?>
    @Transient
    var activation: (Double) -> Double
    @Transient
    var derivative: (Double) -> Double

    constructor(learningRate: Double, activation: (Double) -> Double, derivative: (Double) -> Double, vararg sizes: Int) {
        this.learningRate = learningRate
        this.activation = activation
        this.derivative = derivative
        this.layers = arrayOfNulls<Layer>(sizes.size)
        for (i in sizes.indices) {
            var nextSize = 0;
            if (i < sizes.size - 1) {
                nextSize = sizes[i + 1]
            }
            layers[i] = Layer(sizes[i], nextSize)
            for (j in 0 until sizes[i]) {
                layers[i]?.biases?.set(j, Random.nextDouble() * 2.0 - 1.0)
                for (k in 0 until nextSize) {
                    layers[i]?.weights?.get(j)?.set(k, Random.nextDouble() * 2.0 - 1.0)
                }
            }
        }
    }

    fun getResultOutputs(inputs: DoubleArray): DoubleArray {
        System.arraycopy(inputs, 0, layers[0]!!.neurons, 0, inputs.size)
        for (i in 1 until layers.size) {
            var previousLayer = layers[i - 1]
            var currentLayer = layers[i]
            for (j in 0 until currentLayer!!.size) {
                currentLayer.neurons[j] = 0.0;
                for (k in 0 until previousLayer!!.size) {
                    currentLayer.neurons[j] += previousLayer.neurons[k] * previousLayer.weights[k][j]!!
                }
                currentLayer.neurons[j] += currentLayer.biases[j]
                currentLayer.neurons[j] = activation.invoke(currentLayer.neurons[j])
            }
        }
        return layers.last()!!.neurons
    }

    fun doBackpropagation(targets: DoubleArray) {
        var errors = DoubleArray(layers[layers.size - 1]!!.size)
        for (i in 0 until layers[layers.size - 1]!!.size) {
            errors[i] = targets[i] - layers[layers.size - 1]!!.neurons[i]
        }
        for (k in layers.size - 2 downTo 0) {
            val l = layers[k]!!
            val l1 = layers[k + 1]!!
            val errorsNext = DoubleArray(l.size)
            val gradients = DoubleArray(l1.size)
            for (i in 0 until l1.size) {
                gradients[i] = errors[i] * derivative.invoke(layers[k + 1]!!.neurons[i])
                gradients[i] *= learningRate
            }
            val deltas = Array(l1.size) { DoubleArray(l.size) }
            for (i in 0 until l1.size) {
                for (j in 0 until l.size) {
                    deltas[i][j] = gradients[i] * l.neurons[j]
                }
            }
            for (i in 0 until l.size) {
                errorsNext[i] = 0.0
                for (j in 0 until l1.size) {
                    errorsNext[i] += l.weights[i][j]!! * errors[j]
                }
            }
            errors = DoubleArray(l.size)
            System.arraycopy(errorsNext, 0, errors, 0, l.size)
            val weightsNew = Array(l.weights.size) { DoubleArray(l.weights[0].size) }
            for (i in 0 until l1.size) {
                for (j in 0 until l.size) {
                    weightsNew[j][i] = l.weights[j][i]!! + deltas[i][j]
                }
            }
            l.weights = weightsNew
            for (i in 0 until l1.size) {
                l1.biases[i] += gradients[i]
            }
        }
    }

}