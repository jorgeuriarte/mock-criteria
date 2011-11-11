package mockcriteria;

class CriteriaMocker {
	def element
	boolean fits = true
	def CriteriaMocker(element) {
		println "ELemento: ${element}"
		this.element = element
	}

	def eq(a, b) {
		if (fits) {
			fits = (element."${a}" == b)
		}
	}

	def eqProperty(a, b) {
		if (fits) {
			fits = (element."${a}" == element."${b}")
		}
	}

	def gt(a, b) {
		if (fits) {
			fits = (element."${a}" > b)
		}
	}

	def gtProperty(a, b) {
		if (fits) {
			fits = (element."${a}" > element."${b}")
		}
	}

	def ge(a, b) {
		if (fits) {
			fits = (element."${a}" >= b)
		}
	}

	def geProperty(a, b) {
		if (fits) {
			fits = (element."${a}" >= element."${b}")
		}
	}
	def lt(a, b) {
		if (fits) {
			fits = (element."${a}" < b)
		}
	}

	def ltProperty(a, b) {
		if (fits) {
			fits = (element."${a}" <= element."${b}")
		}
	}

	def le(a, b) {
		if (fits) {
			fits = (element."${a}" <= b)
		}
	}

	def leProperty(a, b) {
		if (fits) {
			fits = (element."${a}" <= element."${b}")
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
