import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.*
import java.util.*
import javax.imageio.ImageIO
import kotlin.math.exp

fun main() {
    val writer = FileWriter("stats.txt")
    val images = arrayOfNulls<BufferedImage>(201)
    val imagesFiles = File("./new").listFiles()
    val digits = IntArray(201)
    for (i in imagesFiles.indices) {
        val name = imagesFiles[i].name
        images[i] =ImageIO.read(imagesFiles[i])
        digits[i] = (imagesFiles[i].name[20].toString() + "").toInt() // passing answer
    }
    val inputs = Array(201) { DoubleArray(10000) }
    for (i in 0 until 201) {
        for (x in 0..99) {
            for (y in 0..99) {
                inputs[i][x + y * 100] = (images[i]!!.getRGB(x, y) and 0xff) / 255.0 // входные значения
            }
        }
    }
    val sigmoid = { x: Double -> 1 / (1 + exp(-x)) }
    val dsigmoid = {x: Double -> x * (1 - x)}
//    val nn = NeuralNetwork(0.001,sigmoid, dsigmoid, 10000, 512, 128, 32, 6 )
    val nn = getObject()
    nn!!.activation = sigmoid
    nn.derivative = dsigmoid
    val epochs = 1000
    for (i in 1 until epochs) {
        var right = 0
        var errorSum = 0.0
        val batchSize = 100
        for (j in 0 until batchSize) {
            val imgIndex = (Math.random() * 201).toInt()
            val targets = DoubleArray(6)
            val digit: Int = digits.get(imgIndex)
            targets[digit] = 1.0
            val outputs: DoubleArray = nn!!.getResultOutputs(inputs[imgIndex])
            var maxDigit = 0
            var maxDigitWeight = -1.0
            for (k in 0..5) {
                if (outputs[k] > maxDigitWeight) {
                    maxDigitWeight = outputs[k]
                    maxDigit = k
                }
            }
            if (digit == maxDigit) right++
            for (k in 0..5) {
                errorSum += (targets[k] - outputs[k]) * (targets[k] - outputs[k])
            }
            nn.doBackpropagation(targets)
        }
        writer.append("$i $right $errorSum\n")
        println("$i $right $errorSum")
    }
//    saveObject(nn)
    writeAnswers(nn, inputs, images)
    writer.flush()
}

fun saveObject(nn: NeuralNetwork?) {
    try {
        val file = FileOutputStream("nn.ser")
        val out = ObjectOutputStream(file)
        out.writeObject(nn)
        out.close()
        file.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun getObject(): NeuralNetwork? {
    var nn: NeuralNetwork? = null
    try {
        // Reading the object from a file
        val file = FileInputStream("nn.ser")
        val inputStream = ObjectInputStream(file)

        // Method for deserialization of object
        nn = inputStream.readObject() as NeuralNetwork
        inputStream.close()
        file.close()
    } catch (ex: IOException) {
        println("IOException is caught")
    } catch (ex: ClassNotFoundException) {
        println("ClassNotFoundException is caught")
    }
    return nn
}

fun writeAnswers(nn: NeuralNetwork, inputs: Array<DoubleArray>, images: Array<BufferedImage?>) {
    for (i in 0..9) {
        val idx = (Math.random() * 200).toInt()
        val outputs: DoubleArray = nn.getResultOutputs(inputs[idx])
        var maxNeuron = 0.0
        var maxDigit = -1
        for (j in 0..5) {
            if (maxNeuron < outputs[j]) {
                maxNeuron = outputs[j]
                maxDigit = j
            }
        }
        writePicAnswer(images[idx]!!, maxDigit)
        println(maxDigit)
    }
}

fun writePicAnswer(image: BufferedImage, answer: Int) {
    val red = Color(255, 255, 255)
    when (answer) {
        0 -> {
            image.setRGB(0, 0, red.rgb)
            image.setRGB(1, 0, red.rgb)
            image.setRGB(2, 0, red.rgb)
            image.setRGB(2, 1, red.rgb)
            image.setRGB(2, 2, red.rgb)
            image.setRGB(2, 3, red.rgb)
            image.setRGB(2, 4, red.rgb)
            image.setRGB(1, 4, red.rgb)
            image.setRGB(0, 4, red.rgb)
            image.setRGB(0, 3, red.rgb)
            image.setRGB(0, 2, red.rgb)
            image.setRGB(0, 1, red.rgb)
        }
        1 -> {
            image.setRGB(2, 0, red.rgb)
            image.setRGB(2, 1, red.rgb)
            image.setRGB(2, 2, red.rgb)
            image.setRGB(2, 3, red.rgb)
        }
        2 -> {
            image.setRGB(0, 0, red.rgb)
            image.setRGB(1, 0, red.rgb)
            image.setRGB(2, 0, red.rgb)
            image.setRGB(2, 1, red.rgb)
            image.setRGB(2, 2, red.rgb)
            image.setRGB(1, 2, red.rgb)
            image.setRGB(0, 2, red.rgb)
            image.setRGB(0, 3, red.rgb)
            image.setRGB(0, 4, red.rgb)
            image.setRGB(1, 4, red.rgb)
            image.setRGB(2, 4, red.rgb)
        }
        3 -> {
            image.setRGB(0, 0, red.rgb)
            image.setRGB(1, 0, red.rgb)
            image.setRGB(2, 0, red.rgb)
            image.setRGB(2, 1, red.rgb)
            image.setRGB(2, 2, red.rgb)
            image.setRGB(1, 2, red.rgb)
            image.setRGB(2, 3, red.rgb)
            image.setRGB(2, 4, red.rgb)
            image.setRGB(1, 4, red.rgb)
            image.setRGB(0, 4, red.rgb)
        }
        4 -> {
            image.setRGB(0, 0, red.rgb)
            image.setRGB(0, 1, red.rgb)
            image.setRGB(0, 2, red.rgb)
            image.setRGB(1, 2, red.rgb)
            image.setRGB(2, 0, red.rgb)
            image.setRGB(2, 1, red.rgb)
            image.setRGB(2, 2, red.rgb)
            image.setRGB(2, 3, red.rgb)
            image.setRGB(2, 4, red.rgb)
        }
        5 -> {
            image.setRGB(0, 0, red.rgb)
            image.setRGB(1, 0, red.rgb)
            image.setRGB(2, 0, red.rgb)
            image.setRGB(0, 1, red.rgb)
            image.setRGB(2, 2, red.rgb)
            image.setRGB(1, 2, red.rgb)
            image.setRGB(0, 2, red.rgb)
            image.setRGB(2, 3, red.rgb)
            image.setRGB(0, 4, red.rgb)
            image.setRGB(1, 4, red.rgb)
            image.setRGB(2, 4, red.rgb)
        }
    }
    val file = File("./output/" + UUID.randomUUID().toString() + ".jpg")
    try {
        ImageIO.write(image, "jpg", file)
    } catch (e: IOException) {
        throw IllegalStateException(e)
    }
}