// TODO Somehow consider 100%-copying this package from javax.json
// Available options:
// - A pure OOP approach (requires multiple (+abstract) classes, complicates design by exploding the number of required types)
// - A codegen for generate-sources?
// - A post-compilation processing for the class files (the maven-shade-plugin does not seem to be an option here)?
/**
 * Contains stuff for the {@link jakarta.json} package.
 */
@NonnullByDefault
package lsh.ext.gson.ext.jakarta.json;

import lsh.NonnullByDefault;
