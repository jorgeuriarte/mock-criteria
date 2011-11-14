package mockcriteria;

class MockCriteriaMixin {

	def mockCriteria(Class theclass, List data) {
		CriteriaMocker.datos = data
		theclass.metaClass.static.createCriteria = {CriteriaMocker.crit}
	}		

}