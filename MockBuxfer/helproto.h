User * make_user(const char *user_name);
Group * create_group(const char *group_name);
User *find_poor_user(User *traveller, double amount);
void order_user(Group *group, User *order_me, User *previous);