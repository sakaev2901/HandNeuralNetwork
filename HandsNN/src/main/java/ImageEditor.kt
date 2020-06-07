import net.coobird.thumbnailator.Thumbnails
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File

class ImageEditor {

    fun compressImages(images: Array<BufferedImage>, imagesFiles: List<File>) {
        for (i in images.indices) {
            var name = imagesFiles[i].name
            Thumbnails.of(images[i])
                    .size(100, 100)
                    .toFile("./small/$name")
        }
    }

    fun separateHand(images: Array<BufferedImage>) {
        for (i in images.indices) {
            for (k in 0..99) {
                for (j in 0..99) {
                    val color = Color(images[i]!!.getRGB(k, j))
                    val r = color.red
                    val g = color.green
                    val b = color.blue
                    println("$r $g $b")
                    val black = Color(0, 0, 0)
                    val white = Color(255, 255, 255)
                    if ((r < b) || (r > 200 && g > 200 && b > 200)) {
                        images[i]?.setRGB(k, j, black.rgb)
                    } else {
                        images[i]?.setRGB(k, j, white.rgb)
                    }
                    print(' ')
                }
                println()
            }
        }
        //        val file = File("./new/$name")
//        ImageIO.write(images[i], "jpg", file)
    }

    fun writeAnswer(answer: Int, image: BufferedImage)  {
        val red = Color(255, 255, 255)
        val graphics: Graphics = image.graphics
        val f = Font(Font.SERIF, Font.PLAIN, 15)
        graphics.font = f
        graphics.color = Color.BLUE
        graphics.drawString("Hello", 0, 50)
        graphics.dispose()
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
    }


}