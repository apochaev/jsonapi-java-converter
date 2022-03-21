package us.pochaev.jsonapi.v1_0.converter.to.membername;

import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiSpecificationException;

/**
 * Factory that creates valid member names according to the
 * <a href="https://jsonapi.org/format/1.0/#document-member-names">specification</a>.
 *
 * @author apochaev
 *
 */
public class MemberNameFactory {

	/**
	 * Returns a valid member name or throws {@link JsonApiSpecificationException}.
	 * @param name Member name candidate.
	 * @return
	 */
	public static String create(String name) {
		return Validator.validate(name);
	}
}
