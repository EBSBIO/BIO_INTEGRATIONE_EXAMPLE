package ru.rtlabs.ebs.reference.receiver.marshalling.jwt;

/**
 * Объект JWT.
 *
 * @param header  содержимое в header.
 * @param payload содержимое в payload.
 * @param <H>     объект для header.
 * @param <P>     объект для payload.
 */
public record BaseJwt<H extends BaseHeader, P extends BasePayload>(H header, P payload) {
}
