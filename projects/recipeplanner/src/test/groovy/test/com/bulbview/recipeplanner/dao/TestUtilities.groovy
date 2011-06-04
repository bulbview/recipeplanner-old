package test.com.bulbview.recipeplanner.dao

import com.bulbview.recipeplanner.datamodel.Entity
import com.bulbview.recipeplanner.persistence.EntityDao


class TestUtilities <T extends Entity> {
    def  EntityDao<T> dao
    def Class<T> type

    def T createAndSaveEntityWithName(String name) {
        def Entity entity = type.newInstance()
        entity.setName(name)
        def savedEntity = dao.save(entity)
        return savedEntity
    }
}
