all: clean
	javac -cp ./build src/dinah/sql/SQLClause.java -d ./build
	javac -cp ./build src/dinah/**/**/*.java -d ./build
	javac -cp ./build src/dinah/**/*.java -d ./build
	
clean:
	rm -rf ./build
	mkdir build
	find -name "*~" | xargs rm -rf

shell:
	java -cp ./build dinah.util.Shell

run:
	java -cp ./build dinah.core.Dinah

test:
	java -cp ./build dinah.data.DataFile
