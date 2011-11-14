package mockcriteria

import grails.test.*

@Mixin(mockcriteria.MockCriteriaMixin)
class DomainTests extends GrailsUnitTestCase {

	def domains

    protected void setUp() {
        domains = [new Domain(id:1, oldid: 1, name:"jorge", address:"Bilbao", active:true),
        		   new Domain(id:2, oldid: 2, name:"pepe", address:"Bilbao", active:true),
        		   new Domain(id:3, oldid: 4, name:"joseba", address:"Miribilla", active: false),
        		   new Domain(id:4, oldid: 3, name:"eloy", address:"Bilbao", active:false),
        		   new Domain(id:5, oldid: 4, name:"Getxo", address:"Getxo", active:false)
        ]
        mockCriteria(Domain, domains)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testEmptyFilterReturnsFullList() {
		assert domains.size() == results {
			
		}.size()
    }

    void testEqFilterOnBoolean() {
    	assert 2 == results {
    		eq('active', true)
    	}.size()
    }

    void testEqFilterOnNumber() {
    	assert "joseba" == results {
    		eq('id', 3)
    	}[0].name
    }

    void testEqFilterOnString() {
    	assert 3 == results {
    		eq('name', 'joseba')
    	}[0].id
    }

    void testMultipleEqOnSring() {
    	assert 2 == results {
    		eq('address', 'Bilbao')
    		eq('active', true)
    	}.size()
    }

    void testGtGeLtLeOnNumber() {
    	assert (domains.size() - 1) == results {
    		gt('id', 1)
    	}.size()
    	assert (domains.size()) == results {
    		ge('id', 1)
    	}.size()
    	assert 1 == results {
    		lt('id', 2)
    	}.size()
    	assert 2 == results {
    		le('id', 2)
    	}.size()
    }

    void testLikeOnString() {
    	assert 3 == results {
    		like('address', 'Bil%')
    	}.size()
    	assert 4 == results {
    		like('address', '%il%')
    	}.size()
    	assert 1 == results {
    		like('address', '%iribil%')
    	}.size()
    	assert 1 == results {
    		like('address', '%lla')
    	}.size()
    	assert 0 == results {
    		like('address', 'Bil')
    	}.size()
    	assert 3 == results {
    		like('address', 'B%ao')
    	}.size()
    }

    void testILikeOnString() {
        assert 3 == results {
            ilike('address', 'b%ao')
        }.size()
        assert domains.size() == results {
            ilikeProperty('address', 'address')
        }.size()
    }

    void testEqOnProperties() {
    	assert 2 == results {
    		eqProperty('id', 'oldid')
    	}.size()
    	assert 1 == results {
    		eqProperty('name', 'address')
    	}.size()
    }

    private def results(Closure clos) {
    	Domain.createCriteria().list(clos)
    }
}