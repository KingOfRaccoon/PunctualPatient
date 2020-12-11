fun processor(string: String, array : Array<Int>){
    val secondNumber = string[1].toString().toInt()
    val thirdNumber = check(string[2])
    when(string[0]){
        // запись констант
        '1' -> array[secondNumber] = thirdNumber

        // копирование переменных
        '2' -> array[thirdNumber] = array[secondNumber]

        // сложение
        '3' -> array[thirdNumber] += array[secondNumber]

        // вычитаение
        '4' -> array[thirdNumber] -= array[secondNumber]

        // деление
        '5' -> array[thirdNumber] /= array[secondNumber]

        // умножение
        '6' -> array[thirdNumber] *= array[secondNumber]

        // логическое "И"
        '7' -> array[thirdNumber] = array[thirdNumber] and array[secondNumber]

        // логическое "Или"
        '8' -> array[thirdNumber] = array[thirdNumber] or array[secondNumber]

        // логическое "Исключающее Или"
        '9' -> array[thirdNumber] = array[thirdNumber] xor array[secondNumber]

    }
    array.forEach {
        print("$it ")
    }
    println()
}
fun check(liter : Char): Int{
    var answer = 0
    val arrayLiters = arrayOf('A', 'B', 'C', 'D', 'E', 'F')
    if (liter in arrayLiters)
        when(liter){
            'A' -> answer = 10
            'B' -> answer = 11
            'C' -> answer = 12
            'D' -> answer = 13
            'E' -> answer = 14
            'F' -> answer = 15
        }
    else
        answer = liter.toString().toInt()
    return answer
}

fun main() {
    val array = arrayOf(0, 0, 0, 0)
    var a = readLine()!!
    while (a != "0"){
        processor(a, array)
        a = readLine()!!
    }
}