package mockcriteria

import grails.test.*

class DomainTests extends GrailsUnitTestCase {

	def domains

    protected void setUp() {
        domains = [new Domain(id:1, oldid: 1, name:"jorge", address:"Bilbao", active:true),
        		   new Domain(id:2, oldid: 2, name:"pepe", address:"Bilbao", active:true),
        		   new Domain(id:3, oldid: 4, name:"joseba", address:"Miribilla", active: false),
        		   new Domain(id:4, oldid: 3, name:"eloy", address:"Bilbao", active:false)
        ]
        CriteriaMocker.mockCriteria(Domain, domains)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testEmptyFilterReturnsFullList() {
		assert domains.size() == Domain.createCriteria().list() {
			
		}.size()
    }

    void testEqFilterOnBoolean() {
    	assert 2 == Domain.createCriteria().list() {
    		eq('active', true)
    	}.size()
    }

    void testEqFilterOnNumber() {
    	assert "joseba" == Domain.createCriteria().list() {
    		eq('id', 3)
    	}[0].name
    }

    void testEqFilterOnString() {
    	assert 3 == Domain.createCriteria().list() {
    		eq('name', 'joseba')
    	}[0].id
    }

    void testMultipleEqOnSring() {
    	assert 2 == Domain.createCriteria().list() {
    		eq('address', 'Bilbao')
    		eq('active', true)
    	}.size()
    }

    void testGtGeLtLeOnNumber() {
    	assert (domains.size() - 1) == Domain.createCriteria().list() {
    		gt('id', 1)
    	}.size()
    	assert (domains.size()x) == Domain.createCriteria().list() {
    		ge('id', 1)
    	}.size()
    	assert 1 == Domain.createCriteria().list() {
    		lt('id', 2)
    	}.size()
    	assert 2 == Domain.createCriteria().list() {
    		le('id', 2)
    	}.size()
    }

}
