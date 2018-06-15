package testpage

import org.scalajs.dom.{Event, Node, document}
import com.thoughtworks.binding._
import com.thoughtworks.binding.Binding._
import org.scalajs.dom.html.{Button, TableRow}

object app {
	case class Contact(name: Var[String], email: Var[String])

	val data = Vars.empty[Contact]

	@dom
	def bindingButton(contact: Contact): Binding[Button] = {
		<button
			onclick={ event: Event => contact.name.value = "Modified Name" }
		>
			Modify the name
		</button>
	}

	@dom
	def bindingTr(contact: Contact): Binding[TableRow] = {
		<tr>
			<td>{ contact.name.bind }</td>
			<td>{ contact.email.bind }</td>
			<td>{ bindingButton(contact).bind }</td>
		</tr>
	}

	@dom
	def bindingTable(contacts: BindingSeq[Contact]): Binding[BindingSeq[Node]] = {
		<div>
			<button
			onclick={ event: Event => data.value += Contact(Var("Yang Bo"), Var("yang.bo@rea-group.com")) }
			>
				Add a contact
			</button>
		</div>
		<table>
			<tbody>
			{
				for (contact <- contacts) yield {
					bindingTr(contact).bind
				}
			}
			</tbody>
		</table>
	}

	def main(args: Array[String]): Unit = {
		dom.render(document.body, bindingTable(data))
	}
}