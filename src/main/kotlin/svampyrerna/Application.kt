package svampyrerna

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("svampyrerna")
		.start()
}

