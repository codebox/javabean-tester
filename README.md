# javabean-tester
Do you unit test your JavaBeans? I'm talking about really simple classes with just get/set methods and no business logic at all? Many developers, even those who are very keen on unit testing, don't bother - a commonly stated reason is that such classes are too simple to go wrong, and it just isn't worth the investment of time to write and maintain the tests.

I'd like to try to convince you that it's entirely possible for JavaBeans to contain bugs, and that with the help of the JavaBeanTester class described below, you only need to write 1 line of code to fully test an entire bean regardless of size.

So how is it possible to get one of these classes wrong? I'll demonstrate by showing you 3 JavaBean bugs that I've seen in real code, two of them were actually running on production systems. Can you spot the problem here?

<pre>public String getAddress4(){
    return address4;
}

public void setAddress4(String address4){
    this.address4 = address4;
}

public String getAddress5(){
    return address5;
}

public void setAddress5(String address5){
    this.address4 = address5;
}</pre>

It's pretty easy to find if you are looking for it, but this class had dozens of properties and the problem went unnoticed for some time. The get/set methods of the class had originally been created using the 'Generate Getters and Setters' feature in Eclipse, which writes code for you based on the member variables of the class. To start with there were only 4 address fields, but someone decided that a fifth address line might be useful, so a developer copied and pasted the getAddress4()/setAddress4() methods and renamed things by hand; unfortunately they missed one of the required changes and the setAddress5() method assigned its value to address4 instead of address5.

A second variant of this bug, which was actually caught by running JavaBeanTester before the code made it into production looked like this:

<pre>public void setTelephone1(String telephone1){
    this.telephone1 = telephone1;
}

public void setTelephone2(String telephone2){
    this.telephone1 = telephone1;
}</pre>

another copy/paste error - this time the value passed into the second 'set' method is ignored and the member variable telephone1 is assigned back to itself.

The third bug looked like this:

<pre>public void setName(String name){
    name = name;
}</pre>

the developer wrote this method by hand and missed off the 'this.' prefix on the left-hand side of the assignment. It's worth noting that all of these bugs will generate compiler warnings due to variables never being read/written, or being ignored entirely - illustrating why it's a good idea to eliminate all warnings from your code-base to make stuff like this stand out.

Since JavaBeans are simple to write, they should be simple to test as well. The JavaBeanTester class uses reflection to find all the get/set methods in a class, it then generates an appropriate value for each set method, calls the method, and then checks that the value that comes out of the get method is the same as the value that went in. You can [download the source code](/downloads/JavaBeanTester.java), or take a look at the [GitHub project](https://github.com/codebox/javabean-tester). To use the JavaBeanTester just call its static test() method, passing in a reference to the class you want to test:

<pre>@Test
public void testBeanProperties(){
    JavaBeanTester.test(MyBean.class);
}</pre>

the JavaBeanTester class will fail the test if the value returned by any getter method does not match the value supplied to the setter method. If your bean has some get/set methods that don't simply store/retrieve values, and you want to skip those properties and test them separately, then just supply the names of the properties as further arguments to the test method following the class value, for example:

<pre>@Test
public void testBeanProperties(){
 // Does not test the getPostcode/setPostcode or getDate/setDate methods
    JavaBeanTester.test(MyBean.class, "postcode", "date");
}</pre>

Since the list of bean methods is inspected at runtime, there is very little maintenance overhead associated with this type of test - any methods that you add or rename after writing the test code are picked up automatically.
