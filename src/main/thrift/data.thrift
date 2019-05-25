//包名的名字
namespace java thrift.generated
//这东西是让其可直接使用short int 看成别名的意思也行
typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String
//这是个对象 关键字struct很重要
struct Person{
1: optional String username,
2: optional int age,
3: optional boolean married;
}
//这玩意是异常处理
exception DataException {
1: optional String message,
2: optional String callStack,
3: optional String date;
}
service PersonService{
Person getPersonByUsername(1: required String username) throws (1:DataException dataException),
void savePerson(1:required Person person) throws (1:DataException dataException)
}