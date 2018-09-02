#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "lists.h"
#include "helproto.h"

/*
 *Creates and returns a group, given a group name
 */
Group * create_group(const char *group_name) {
  int gn_len = strlen(group_name);
  // first, we make a group
  Group * new_group = (Group *) malloc(sizeof(Group));
  // check if the malloc doesn't work
  if (new_group == NULL){
    // return an error
    perror("malloc");
    exit(1);
  }
  // we have to malloc the name of the group
  new_group->name = malloc(gn_len);
  // check if the malloc doesn't work
  if (new_group->name == NULL){
    // return an error
    perror("malloc");
    exit(1);
  }

  // then we set the name of the new group
  strncpy(new_group->name, group_name, gn_len);
  new_group->name[gn_len] = '\0';
  // then return the new group
  return new_group;
}

/*
 *Given a username, this function creates and returns a user
 */
User * make_user(const char *user_name) {
  // make a new user
  User * new_user = (User *) malloc(sizeof(User));
  // check if the malloc doesn't work
  if (new_user == NULL){
    // return an error if it doesn't
    perror("malloc");
    exit(1);
  }
  // we have to define the user's name
  int un_len = strlen(user_name);
  new_user->name = malloc(un_len);
  // check if malloc doesn't work
  if (new_user->name == NULL){
    // return an error if it doesn't
    perror("malloc");
    exit(1);
  }
  // copy the name if successful
  strncpy(new_user->name, user_name, un_len);
  new_user->name[un_len] = '\0';
  // their initial balance is 0
  new_user->balance = 0;
  // then we return the user
  return new_user;
}

/*
 * Finds and returns the richest user that has a balance less than the
 * amount specified. If the traveller's balance is greater than the
 * amount, this method returns the traveller
 */
User *find_poor_user(User *traveller, double amount) {
  if (traveller == NULL){
    return NULL;
  }
  // check if the traveller's current balance is
  // greater than the amount specified
  else if (traveller->balance > amount){
    return traveller;
  }
  // check if the next node is null
  else if (traveller->next == NULL){
    // if it is, then we return this node
    return traveller;
  }
  // otherwise, we can assume that there is a next node
  // we check the amounts
  else if (traveller->next->balance > amount){
    // we return this traveller
    return traveller;
  }
  // otherwise we go to the next user
  else{
    User * returner = find_poor_user(traveller->next, amount);
    return returner;
  }
}

/*
 * Given a group, a user to order, and the user before the user to order
 * this method will order the user in the group. If the user to order is
 * the head of the user list of the group, this method assumes that the
 * "previous" user is the head as well.
 */
void order_user(Group *group, User *previous, User *order_me) {
  // a few base cases

  // if order_me and previous are the same, and the next node
  // is NULL, 
  int long_test = strcmp(order_me->name, previous->name) == 0;
  int short_test = order_me->next == NULL;
  if (long_test && short_test){
    // then we can conclude that there is one node, so we don't
    // do anything
    return;
  }
  // the next case is if previous' balance is less than order_me
  // and the next node is null
  int med_test = order_me->balance > previous->balance;
  if (med_test && short_test){
    // we don't do anything since it is still in order
    return;
  }

  // otherwise, we can conclude that order_me is almost at an
  // arbitrary node

  // check if we are at the head
  if (strcmp(previous->name, order_me->name) == 0){
    // if we are at the head, then we have to remove
    // the node that we want to order
    group->users = order_me->next;
  }
  else{
    // otherwise, if both nodes are not the same, we have to
    // remove order_me from the linked list
    previous->next = order_me->next;
  }

  // define a balance
  double om_balance = order_me->balance;

  // once we remove that node, we want to find the richest
  // person who has the balance less that order_me's balance
  User * poorer = find_poor_user(group->users, om_balance);
  // if for some reason, the poor person's balance is the
  // same as order_me (i.e. the head), we just prepend the
  // user
  if (poorer->balance >= om_balance){
    order_me->next = group->users;
    group->users = order_me;
  }
  else{
    // otherwise, we can assume that the poor person
    // is strictly poorer than order_me
    // and order_me is strictly poorer
    //  than poor person's next
    order_me->next = poorer->next;
    // then we set the poor person's next to order_me
    poorer->next = order_me;
  }
  return;
}

/* Add a group with name group_name to the group_list referred to by
* group_list_ptr. The groups are ordered by the time that the group was
* added to the list with new groups added to the end of the list.
*
* Returns 0 on success and -1 if a group with this name already exists.
*
* (I.e, allocate and initialize a Group struct, and insert it
* into the group_list. Note that the head of the group list might change
* which is why the first argument is a double pointer.)
*/
int add_group(Group **group_list_ptr, const char *group_name) {
  // check if the pointer is NULL
  if (*group_list_ptr == NULL){
      // just create a new group
      Group * new_group = create_group(group_name);
      // and then set the group pointer to be the
      // new group
      *group_list_ptr = new_group;
  }
  else{
    // otherwise, we can assume that the group list exists
    Group * traveller = *group_list_ptr;
    // and we traverse through the list
    while (traveller->next != NULL){
      // check if the names are the same
      if (strcmp(traveller->name, group_name) == 0){
          // if it is, then return -1
          return -1;
      }
      // then traverse it
      traveller = traveller->next;
    }
    // check if the group's names are the same since we
    // are checking the last node
    if (strcmp(traveller->name, group_name) == 0){
      // if it is, then return -1 as instructed
      return -1;
    }
    // otherwise we just greate a group
    Group * new_group = create_group(group_name);
    // next, we can link the traveller to the new group
    traveller->next = new_group;
    }
  return 0;
}

/* Print to standard output the names of all groups in group_list, one name
 * per line. Output is in the same order as group_list.
 */
void list_groups(Group *group_list) {
  // check if the pointer is not null
  while (group_list != NULL){
    // if it isn't then we print out the group
    fprintf(stdout, "%s\n", group_list->name);
    // then just traverse to the next group
    group_list = group_list->next;
    }
}

/* Search the list of groups for a group with matching group_name
 * If group_name is not found, return NULL, otherwise return a pointer to the
 * matching group list node.
 */
Group *find_group(Group *group_list, const char *group_name) {
  // go through the group
  while (group_list != NULL){
    // check if the name is the same
    if (strcmp(group_list->name, group_name) == 0){
      // if it is the same, return the group
      return group_list;
    }
    // otherwise we traverse through
    group_list = group_list->next;
  }
  // otherwise it is conclusive that there doesn't exist a group
  return NULL;
}

/* Add a new user with the specified user name to the specified group. Return zero
 * on success and -1 if the group already has a user with that name.
 * (allocate and initialize a User data structure and insert it into the
 * appropriate group list)
 */
int add_user(Group *group, const char *user_name) {
  // first we get the node previous to the indicated user name
  User *previous = find_prev_user(group, user_name);

  // if there exists a node
  if (previous != NULL){
    // then return -1 since there is a user
    return -1;
  }

  // in this case, we just need to prepend
  User * first_node = group->users;
  // we create a user
  User * new_user = make_user(user_name);
  // get the new user to point to the first node
  new_user->next = first_node;
  // then set the first pointer to the newly created node
  group->users = new_user;
  // from here, we can order the user properly (in the 
  // event that there are negative balances)
  order_user(group, group->users, new_user);
  return 0;
}

/* Remove the user with matching user and group name and
* remove all her transactions from the transaction list.
* Return 0 on success, and -1 if no matching user exists.
* Remember to free memory no longer needed.
* (Wait on implementing the removal of the user's transactions until you
* get to Part III below, when you will implement transactions.)
*/
int remove_user(Group *group, const char *user_name) {
  // we want the node previous to the one we want to remove
  User *previous = find_prev_user(group, user_name);
  // we check if there exist such a user
  if (previous == NULL){
    // if there doesnt exist a user, we return -1 as instructed
    return -1;
  }
  else{
    // otherwise
    // base case: the "previous" node is the head
    // so we check if the head's name is the same
    User * head = group->users;
    if (strcmp(head->name, user_name) == 0){
      // if it is, then we set the node after that to
      // be the identifier of users
      group->users = head->next;
      // and we want to free the head
      free(head);
    }
    else{
      // "save" the node we want to remove for freeing
      User * saver = previous->next;
      //  we're removing some arbitrary node
      // [prev|-]-->[x|-]-->[other node or null]
      // we remove x in this case,
      // so we get x's "next" pointer
      User * x_ptr = previous->next->next;
      // then we set previous' next to the x pointer
      previous->next = x_ptr;
      // then we want to free the node we want to remove
      free(saver);
    }
    // then remove any transactions associated with that user
	  remove_xct(group, user_name);
  }
  return 0;
}

/* Print to standard output the names of all the users in group, one
* per line, and in the order that users are stored in the list, namely
* lowest payer first.
*/
void list_users(Group *group) {
  // get the user pointer
  User * traveller = group->users;
  // just traverse through the users
  while (traveller != NULL){
    fprintf(stdout, "%.2f %s\n", traveller->balance, traveller->name);
    traveller = traveller->next;
  }
}

/* Print to standard output the balance of the specified user. Return 0
 * on success, or -1 if the user with the given name is not in the group.
 */
int user_balance(Group *group, const char *user_name) {
  // get the user previous to username
  User *previous = find_prev_user(group, user_name);

  // check if it is null
  if (previous == NULL){
    // if it is null return -1 since that user is not in the group
    return -1;
  }

  // otherwise, we would have to check if the user we want to check
  // is the head of the group; this is done by checking if previous
  // and user name are the same names
  if (strcmp(user_name, previous->name) == 0){
    // if they are, just return the balance of the head
    fprintf(stdout, "%.2lf\n", previous->balance);
  }
  else{
    // otherwise, if they aren't then we can safely assume that
    // there is a next node that is the user we want to check
    fprintf(stdout, "%.2lf\n", previous->next->balance);
  }
  return 0;
}

/* Print to standard output the name of the user who has paid the least
 * If there are several users with equal least amounts, all names are output.
 * Returns 0 on success, and -1 if the list of users is empty.
 * (This should be easy, since your list is sorted by balance).
 */
int under_paid(Group *group) {
  // check if the list of users is empty
  if (group->users == NULL){
    // return -1 as instructed
    return -1;
  }
  // we want to keep track of the initial amount
  double amount;
  // so we get the pointer to the person who paid the least
  User * least_paid = group->users;
  // get the least paid amount
  amount = least_paid->balance;
  // we check the least paid
  while (least_paid != NULL){
    // check if the balance is the same
    if (least_paid->balance == amount){
      // if it is the same, print that person
      fprintf(stdout, "%s\n", least_paid->name);
    }
    // if there is, we check the next person
    least_paid = least_paid->next;
  }
  return 0;
}

/* Return a pointer to the user prior to the one in group with user_name. If
 * the matching user is the first in the list (i.e. there is no prior user in
 * the list), return a pointer to the matching user itself. If no matching user
 * exists, return NULL.
 *
 * The reason for returning the prior user is that returning the matching user
 * itself does not allow us to change the user that occurs before the
 * matching user, and some of the functions you will implement require that
 * we be able to do this.
 */
User *find_prev_user(Group *group, const char *user_name) {
  // initialize a user
  User * user = group->users;
  // check if the user is NULL
  if (user != NULL){
    // base case: check if its the first node
    if (strcmp(user->name, user_name) == 0){
      // if it is, then return the first node
      return user;
    }
    // otherwise, if it isn't then we check if its next node is NULL
    while (user->next != NULL){
      // if it's not, then  we check the next node's name
      char * name = user->next->name;
      // then we want to comapre strings
      if (strcmp(name, user_name) == 0){
        // if it's the same name, then just return this current
        // node
        return user;
      }
      // traverse
      user = user->next;
    }
  }
  // if we traversed through the linked list, then there doesn't exist
  // such a node
  return NULL;
}

/* Add the transaction represented by user_name and amount to the appropriate
 * transaction list, and update the balances of the corresponding user and group.
 * Note that updating a user's balance might require the user to be moved to a
 * different position in the list to keep the list in sorted order. Returns 0 on
 * success, and -1 if the specified user does not exist.
 */
int add_xct(Group *group, const char *user_name, double amount) {
  // first, find if there does exist a user who has that name
  User *previous = find_prev_user(group, user_name);

  // if there doesn't exist such a person, we return -1
  if (previous == NULL){
    return -1;
  }

  // otherwise, we can make a transaction
  Xct * new_xct = (Xct *) malloc(sizeof(Xct));
  // check if our malloc doesn't work
  if (new_xct == NULL){
    // if it doesn't then we return an error
    perror("malloc");
    exit(1);
  }
  // set the name of the xct
  int name_len = strlen(user_name);
  new_xct->name = malloc(name_len);
  // check if our malloc doesn't work
  if (new_xct->name == NULL){
    // if it doesn't then we return an error
    perror("malloc");
    exit(1);
  }
  strncpy(new_xct->name, user_name, name_len);
  new_xct->name[name_len] = '\0';
  // set the amount for the transaction
  new_xct->amount = amount;
  // then we want to prepend the transaction to our transactions
  new_xct->next = group->xcts;
  group->xcts = new_xct;

  // from here we want to add balances to the user
  // so we want to make a varaible to ensure what user to re-order
  User * for_ordering;

  // we want to check if the user we added the balance to is the
  // head
  User * head = group->users;
  if (strcmp(head->name, user_name) == 0){
    // if it is, then we just add the balance
    head->balance += amount;
    // and then we set the ordering node to previous
    for_ordering = previous;
  }
  else{
    // if the head is not the user we wanted to change
    // then we can safely assume that there is a next
    // node, so we set that node for ordering
    for_ordering = previous->next;
    // then we add the balance for the node we want to
    // order
    for_ordering->balance += amount;
  }
  // from here, we just order the specified user
  order_user(group, previous, for_ordering);
  return 0;
}

/* Print to standard output the num_xct most recent transactions for the
 * specified group (or fewer transactions if there are less than num_xct
 * transactions posted for this group). The output should have one line per
 * transaction that prints the name and the amount of the transaction. If
 * there are no transactions, this function will print nothing.
 */
void recent_xct(Group *group, long nu_xct) {
  // we set the travelling node
  Xct * traveller = group->xcts;
  // we set an indexer
  long indexer = 0;

  // then we traverse through the nodes

  while (traveller != NULL && indexer < nu_xct){
    // we print out the traveller
    fprintf(stdout, "%.2f %s\n", traveller->amount, traveller->name);
    // increment the index
    indexer++;
    // then the traveller moves along
    traveller = traveller->next;
  }
}

/* Remove all transactions that belong to the user_name from the group's
 * transaction list. This helper function should be called by remove_user.
 * If there are no transactions for this user, the function should do nothing.
 * Remember to free memory no longer needed.
 */
void remove_xct(Group *group, const char *user_name) {
  // we go through the xcts
  Xct * curr = group->xcts;
  Xct * tail = group->xcts;

  while (curr != NULL){
    // base case: the head is a transaction we want
    // to remove
    if (strcmp(user_name, group->xcts->name) == 0){
      // if it is, then we have to set the group's xcts
      // pointer to curr's next pointer
      group->xcts = curr->next;
      // then we free curr
      free(curr);
      // then reset curr to be the head, as well as its tail
      curr = group->xcts;
      tail = group->xcts;
    }
    else{
      // otherwise, if it's not the head, we would have to check
      // if curr's name is the same as the user name
      if (strcmp(curr->name, user_name) == 0){
        // if it is the same, we link the tail to curr's next node
        tail->next = curr->next;
        // then free curr
        free(curr);
        // the tail would be the same then curr would be
        // tail's next
        curr = tail->next;
      }
      else{
        // otherwise, we just traverse forward
        tail = curr;
        curr = curr->next;
      }
    }
  }
  return;
}