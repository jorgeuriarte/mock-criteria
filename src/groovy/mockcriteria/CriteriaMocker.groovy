package mockcriteria;

class CriteriaMocker {
	def element
	boolean fits = true
	def CriteriaMocker(element) {
		this.element = element
	}

	def eq(a, b) {
		test { element."${a}" == b }
	}

	def eqProperty(a, b) {
		test { element."${a}" == element."${b}" }
	}

	def gt(a, b) {
		test { element."${a}" > b }
	}

	def gtProperty(a, b) {
		test { element."${a}" > element."${b}" }
	}

	def ge(a, b) {
		test { element."${a}" >= b }
	}

	def geProperty(a, b) {
		test { element."${a}" >= element."${b}" }
	}
	def lt(a, b) {
		test { element."${a}" < b }
	}

	def ltProperty(a, b) {
		test { element."${a}" <= element."${b}" }
	}

	def le(a, b) {
		test { element."${a}" <= b }
	}

	def leProperty(a, b) {
		test {element."${a}" <= element."${b}"}
	}

	def like(a, b) {
		def pattern = /^${b.replaceAll('%','.+')}$/
		test { element."${a}" ==~ pattern }
	}

	Closure test = { comp ->
		if (fits) {
			fits = comp()
		}
	}

	def itFits() {
		fits
	}

	static def datos
	static def crit = [
		list: { Closure cls ->
			datos.findAll {
				cls.delegate = new CriteriaMocker(it)
				cls.call()
				cls.delegate.itFits()
			}
		}
	]

	static def mockCriteria(theclass, data) {
		this.datos = data
		theclass.metaClass.static.createCriteria = {CriteriaMocker.crit}
	}
}
