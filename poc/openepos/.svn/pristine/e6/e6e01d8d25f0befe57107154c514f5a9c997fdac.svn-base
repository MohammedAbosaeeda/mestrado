all:
	g++ -O2 -o $(app) $(app).cc diffiehellman.cc

arm:
	arm-g++ -O2 -o $(app) $(app).cc diffiehellman.cc
	
tst:
	g++ -O2 -o $(app) $(app).cc diffiehellman.cc
	./$(app)

tsto0:
	g++ -O0 -o $(app) $(app).cc diffiehellman.cc
	./$(app)

prof: clean
	g++ -pg -O0 -o $(app) $(app).cc diffiehellman.cc
	./$(app)
	gprof -b $(app) gmon.out > analysis.txt
	vim analysis.txt

profo2: clean
	g++ -pg -O2 -o $(app) $(app).cc diffiehellman.cc
	./$(app)
	gprof $(app) gmon.out > analysis.txt
	vim analysis.txt

debug: clean
	g++ -ggdb -DDEBUG_BIGNUM -o $(app) $(app).cc diffiehellman.cc
	./$(app) | grep -A 4 HERE | sed 's:.*\(\[.*\]\):\1:g' | sed '/^--$$/d' | ./check_op.py

gdb: clean
	g++ -ggdb -o $(app) $(app).cc diffiehellman.cc
	gdb ./$(app)

clean:
	rm -f bignum_tst diffie_tst
