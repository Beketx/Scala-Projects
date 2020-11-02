package repo

import java.util.UUID

import model.{CreateTodo, Todo, UpdateTodo}

import scala.concurrent.{ExecutionContext, Future}

trait TodoRepository {
  def all(): Future[Seq[Todo]]

  def done(): Future[Seq[Todo]]

  def pending(): Future[Seq[Todo]]

  def create(createTodo:CreateTodo): Future[Todo]

  def createManually(createTodo:Todo): Future[Option[Todo]]

  def get(id: String): Future[Option[Todo]]

  def update(id: String, update: UpdateTodo): Future[Option[Todo]]

  def delete(id: String): Future[Unit]

}


class InMemoryTodoRepository(todo:Seq[Todo] = Seq.empty)(implicit  ex:ExecutionContext) extends TodoRepository {
  private var todos: Vector[Todo] = todo.toVector

  override def all(): Future[Seq[Todo]] = Future.successful(todos)

  override def done(): Future[Seq[Todo]] = Future.successful(todos.filter(_.done))

  override def pending(): Future[Seq[Todo]] = Future.successful(todos.filterNot(_.done))

  override def create(createTodo: CreateTodo): Future[Todo] = Future.successful {
    val todo = Todo(
      id = UUID.randomUUID().toString,
      title = createTodo.title,
      description = createTodo.description,
      done = false
    )
    todos = todos :+ todo
    todo
  }
  override def createManually(oldTodo: Todo): Future[Option[Todo]] = Future.successful {
    val todo = Todo(
      id = oldTodo.id,
      title = oldTodo.title,
      description = oldTodo.description,
      done = oldTodo.done
    )
    todos = todos :+ todo
    Some(todo)
  }

  override def get(id: String): Future[Option[Todo]] = Future{
    todos.find(_.id == id)
  }

  override def delete(id: String): Future[Unit] = Future{
    todos = todos.filterNot(_.id == id)
  }

  override def update(id: String, update: UpdateTodo): Future[Option[Todo]] = {
    def updateEntity(todoSelected: Todo): Todo = {
      val title = update.title.getOrElse(todoSelected.title)
      val description = update.description.getOrElse(todoSelected.description)
      val done = update.done.getOrElse(todoSelected.done)
      Todo(id, title, description, done)
    }
    get(id).flatMap { maybeTodo =>
      maybeTodo match {
        case None => Future { None }
        case Some(todo) =>
          val updatedTodo = updateEntity(todo)
          delete(id).flatMap { _ =>
            createManually(updatedTodo).map(_ => Some(updatedTodo))
          }

      }
    }
  }
}