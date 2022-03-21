package us.pochaev.jsonapi.v1_0.converter.to.exceptions;

import us.pochaev.jsonapi_wip.converter.JsonUtils;

public class JsonApiSpecificationException extends RuntimeException {

	private static final long serialVersionUID = 3291883509863739451L;

	/**
	 * URL of the JSON API Specification version 1.0
	 */
	protected static final String URL_JSONAPI_SPEC_V_1_0 = "https://jsonapi.org/format/1.0";

	/**
	 * URL of the relevant specification section. Defaults to {@link JsonApiSpecificationException#URL_JSONAPI_SPEC_V_1_0}.
	 */
	private final String specUrl;

	/**
	 * Creates an exception with the given message and URL of the JSON API specification version 1.0 documentation root.
	 *
	 * @param message Details of the specification violation. For example: <i>Member names MUST contain at least one character</i>.
	 */
	public JsonApiSpecificationException(String message) {
		super(message);
		specUrl = URL_JSONAPI_SPEC_V_1_0;
	}

	/**
	 * Creates an exception with the given message and URL of the JSON API specification version 1.0 with the given anchor.
	 * @param message Details of the specification violation. For example: <i>Member names MUST contain at least one character</i>.
	 * @param anchor  Anchor name. For example: <i>document-member-names</i> anchor name results in {@link JsonApiSpecificationException#specUrl}
	 * value being set to <i>https://jsonapi.org/format/1.0/#document-member-names</i>.
	 */
	public JsonApiSpecificationException(String message, String anchor) {
		super(message);

		if (anchor != null && anchor.trim().length() > 0) {
			specUrl = URL_JSONAPI_SPEC_V_1_0 + "#" + anchor;
		} else {
			specUrl = URL_JSONAPI_SPEC_V_1_0;
		}
	}

	/**
	 * Returns URL of the relevant specification section. Defaults to {@link JsonApiSpecificationException#URL_JSONAPI_SPEC_V_1_0}.
	 * @return URL of the relevant specification section. For example: <i><a href="https://jsonapi.org/format/1.0/#document-member-names">https://jsonapi.org/format/1.0/#document-member-names</a></i>
	 */
	public String getSpecUrl() {
		return specUrl;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + JsonUtils.toJsonString(this);
	}

}
