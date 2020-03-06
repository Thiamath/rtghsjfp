package com.thiamath.lmassignement.taxService


import spock.lang.Shared
import spock.lang.Specification

class TaxServiceManagerTest extends Specification {

    def "Input/Output test"() {
        given: "a basic configuration for the service"
        def configuration = new Configuration()
        configuration.setBasicTax(new BigDecimal("0.10"))
        configuration.setImportedTax(new BigDecimal("0.05"))

        and: "an item handler"
        def itemServiceHandler = new Ecosystem().itemServiceHandler()

        and: "the tax service manager"
        def taxServiceManager = new TaxServiceManager(configuration, itemServiceHandler)

        expect: "that the tax service processes correctly the inputs"
        taxServiceManager.process(input) == expectedOutput

        where:
        input       || expectedOutput
        ALPHA_INPUT || ALPHA_EXPECT
        BETA_INPUT  || BETA_EXPECT
        GAMMA_INPUT || GAMMA_EXPECT
    }

    @Shared
    def ALPHA_INPUT = """1 book at 12.49
1 music CD at 14.99
1 chocolate bar at 0.85"""
    @Shared
    def BETA_INPUT = """1 imported box of chocolates at 10.00
1 imported bottle of perfume at 47.50"""
    @Shared
    def GAMMA_INPUT = """1 imported bottle of perfume at 27.99
1 bottle of perfume at 18.99
1 packet of headache pills at 9.75
1 box of imported chocolates at 11.25"""

    @Shared
    def ALPHA_EXPECT = """1 book: 12.49
1 music CD: 16.49
1 chocolate bar: 0.85
Sales Taxes: 1.50
Total: 29.83"""
    @Shared
    def BETA_EXPECT = """1 imported box of chocolates: 10.50
1 imported bottle of perfume: 54.65
Sales Taxes: 7.65
Total: 65.15"""
    @Shared
    def GAMMA_EXPECT = """1 imported bottle of perfume: 32.19
1 bottle of perfume: 20.89
1 packet of headache pills: 9.75
1 imported box of chocolates: 11.85
Sales Taxes: 6.70
Total: 74.68"""
}
