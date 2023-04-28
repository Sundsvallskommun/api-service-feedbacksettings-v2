package se.sundsvall.contactsettings.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.contactsettings.api.model.enums.ContactMethod;

@Schema(description = "Contact channel model")
public class ContactChannel {

	@Schema(description = "Method of contact", example = "SMS")
	private ContactMethod contactMethod;

	@Schema(description = "Alias for the destination", example = "Privat mobil")
	private String alias;

	@Schema(description = "Point of destination", example = "0701234567")
	private String destination;

	@Schema(description = "Signal if channel should be used or not when sending message", example = "true")
	private boolean send;

	public static ContactChannel create() {
		return new ContactChannel();
	}

	public ContactMethod getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(ContactMethod contactMethod) {
		this.contactMethod = contactMethod;
	}

	public ContactChannel withContactMethod(ContactMethod contactMethod) {
		this.contactMethod = contactMethod;
		return this;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public ContactChannel withAlias(String alias) {
		this.alias = alias;
		return this;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public ContactChannel withDestination(String destination) {
		this.destination = destination;
		return this;
	}

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public ContactChannel withSend(boolean send) {
		this.send = send;
		return this;
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(contactMethod, alias, destination, send);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ContactChannel)) {
			return false;
		}

		ContactChannel other = (ContactChannel) obj;
		return java.util.Objects.equals(contactMethod, other.contactMethod) && java.util.Objects.equals(alias, other.alias) && java.util.Objects.equals(destination, other.destination) && java.util.Objects.equals(send, other.send);
	}

	@Override
	public String toString() {
		return "ContactChannel [contactMethod=" + contactMethod + ", alias=" + alias + ", destination=" + destination + ", send=" + send + "]";
	}
}
