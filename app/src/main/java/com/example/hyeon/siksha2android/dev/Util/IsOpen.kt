package com.example.hyeon.siksha2android.dev.Util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern

object IsOpen {
    fun isopen(input: String): Boolean {
        val test = "오. [0-9]+시( [0-9]+분)? - 오. [0-9]+시( [0-9]+분)?"
        val newSimpleDateFormat = SimpleDateFormat("kmm", Locale.KOREA)
        val ccurrentTime = Date()
        val mTime = newSimpleDateFormat.format(ccurrentTime)
        val pat = Pattern.compile(test)
        val match = pat.matcher(input)
        val numExtract = Pattern.compile("[0-9]+")
        val interval = intArrayOf(2400, 0)
        while (match.find()) {
            val raw = match.group()
            val parts = raw.split(" - ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in 0..1) {
                val matmat = numExtract.matcher(parts[i])
                var res: String
                if (parts[i].contains("오전")) {
                    matmat.find()
                    res = matmat.group()
                    if (parts[i].contains("분")) {
                        matmat.find()
                        res = res + matmat.group()
                    } else {
                        res = res + "00"
                    }
                } else {
                    matmat.find()
                    res = matmat.group()
                    val intres = Integer.parseInt(res) + 12
                    res = intres.toString()
                    if (parts[i].contains("분")) {
                        matmat.find()
                        res = res + matmat.group()
                    } else {
                        res = res + "00"
                    }
                }
                val resint = Integer.parseInt(res)
                if (i == 0) {
                    if (resint < interval[i]) interval[i] = resint
                } else {
                    if (interval[i] < resint) interval[i] = resint
                }
            }
        }
        val inttime = Integer.parseInt(mTime)
        return interval[0] <= inttime && inttime <= interval[1]
    }
}
