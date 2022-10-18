package fr.dav.lac.kotlincrud.controller

import org.springframework.web.bind.annotation.*

@RequestMapping("entities")
@RestController
class EntityController {

    @GetMapping("/{id}")
    fun getEntity(@PathVariable id: Int): Entity? = Repository.entitiesRepository[id]

    @GetMapping
    fun getAllEntities(): HashMap<Int, Entity> = Repository.entitiesRepository

    @PostMapping
    fun createEntity(@RequestBody entity: Entity): Entity = Repository.addEntity(Entity(entity.name, entity.description))

    @PutMapping("/{id}")
    fun updateEntity(@PathVariable id: Int, @RequestBody entity: Entity): Entity = Repository.updateEntity(entity, id)

    @DeleteMapping("/{id}")
    fun deleteEntity(@PathVariable id: Int) = Repository.deleteEntity(id)

    data class Entity(
        val name: String,
        val description: String
        )

    object Repository{
        var idIncrement: Int = 0
        var entitiesRepository: HashMap<Int, Entity> = hashMapOf()

        fun addEntity(entity: Entity): Entity{
            idIncrement++;
            entitiesRepository[idIncrement] = entity
            return entity
        }

        fun updateEntity(entity: Entity, id: Int): Entity{
            if(!entitiesRepository.containsKey(id)) {
                throw RuntimeException(String.format("Entity with ID='%d' not found", id))
            }
            entitiesRepository[id] = entity
            return entity
        }

        fun deleteEntity(id: Int) {
            if(!entitiesRepository.containsKey(id)) {
                throw RuntimeException(String.format("Entity with ID='%d' not found", id))
            }
            entitiesRepository.remove(id)
        }
    }
}