package test.com.recipeplanner.fixtures

import org.springframework.test.context.TestContextManager

class DirtyTestContextManager(val clazz: Class[_]) extends TestContextManager(clazz) {

    def dirty() {
        getTestContext().markApplicationContextDirty()
    }
}