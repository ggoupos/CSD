/***************************************************************
 *
 * file: pss.h
 *
 * @Author  Nikolaos Vasilikopoulos (nvasilik@csd.uoc.gr), John Petropoulos
 *
 * @brief   Implementation of the "pss.h" header file for the Public Subscribe
 *System, function definitions
 *
 *
 ***************************************************************
 */

#include <stdio.h>
#include <stdlib.h>

#include "pss.h"

extern struct Group groups[MG];

int calcNumber(struct SubInfo **hashtable, int m) {
  int i = 0;
  int counter = 0;
  struct SubInfo *cur = NULL;
  for (i = 0; i < m; i++) {
    if (hashtable[i] != NULL) {
      cur = hashtable[i];
      while (cur != NULL) {
        counter++;
        cur = cur->snext;
      }
    }
  }
  return counter;
}
int isleaf(struct TreeInfo *node) {
  if (node == NULL)
    return -1;
  if (node->tlc == NULL && node->trc == NULL)
    return 1;

  return 0;
}
void printLOList(struct TreeInfo *root) {
  struct TreeInfo *cur = root;
  struct TreeInfo *prev = root;
  while (!isleaf(cur))
    cur = cur->tlc;

  while (cur != NULL) {
    prev = cur;
    printf("%d,", cur->tId);
    cur = cur->right;
  }
  /*printf("\n");
  while (prev != NULL) {
    printf("%d->", prev->ttm);
    prev = prev->left;
  }
  */
}
void printForEach(struct SubInfo *head) {
  struct SubInfo *current = head;
  while (current != NULL) {
    printf("SUBSCRIBERID = <%d>, GROUPLIST = \n", current->sId);
    for (int i = 0; i < MG; i++) {
      if (current->tgp[i] != NULL && current->tgp[i] != (struct TreeInfo *)1) {
        printf("<%d>, TREELIST = <", i);
        printLOList(current->tgp[i]);
        printf(">\n");
      }
    }
    current = current->snext;
  }
}
struct TreeInfo *createLO_node(int id, int tm) {
  struct TreeInfo *newNode = malloc(sizeof(struct TreeInfo));
  newNode->tlc = NULL;
  newNode->trc = NULL;
  newNode->tp = NULL;
  newNode->right = NULL;
  newNode->left = NULL;
  newNode->ttm = tm;
  newNode->tId = id;
  return newNode;
}

int LO_Lookup(struct TreeInfo *root, int tm) {
  struct TreeInfo *current = root;
  while (current != NULL) {
    if (tm > current->ttm)
      current = current->trc;
    else if (tm < current->ttm)
      current = current->tlc;
    else
      return 1;
  }
  return 0;
}

struct TreeInfo *LO_BST_Insert(struct TreeInfo *root,
                               struct TreeInfo *newNode) {

  if (root == NULL) {
    return newNode;
  }
  if (LO_Lookup(root, newNode->ttm)) {
    // printf("Cannot insert same tm");
    return root;
  }

  struct TreeInfo *current = root;
  struct TreeInfo *prev = root;
  if (isleaf(current)) {
    if (newNode->ttm < current->ttm) {
      newNode->trc = current;
      current->tp = newNode;
      struct TreeInfo *secondNewNode =
          createLO_node(newNode->tId, newNode->ttm);
      newNode->tlc = secondNewNode;
      secondNewNode->tp = newNode;
      secondNewNode->right = current;
      current->left = secondNewNode;
      return newNode;
    }
    if (newNode->ttm > current->ttm) {
      current->trc = newNode;
      newNode->tp = current;
      struct TreeInfo *secondNewNode =
          createLO_node(current->tId, current->ttm);
      current->tlc = secondNewNode;
      secondNewNode->tp = current;
      secondNewNode->right = newNode;
      newNode->left = secondNewNode;
      return root;
    }
  }
  while (current != NULL && !isleaf(current)) {
    prev = current;
    if (newNode->ttm < current->ttm)
      current = current->tlc;
    else if (newNode->ttm > current->ttm)
      current = current->trc;
  }

  if (current != NULL) {
    if (newNode->ttm > current->ttm) {
      current->trc = newNode;
      newNode->tp = current;
      struct TreeInfo *secondNewNode =
          createLO_node(current->tId, current->ttm);
      current->tlc = secondNewNode;
      secondNewNode->tp = current;
      current->tlc->right = current->trc;
      current->trc->left = current->tlc;
      if (current->left != NULL) {
        current->tlc->left = current->left;
        current->left->right = current->tlc;
        current->left = NULL;
      }
      if (current->right != NULL) {
        current->trc->right = current->right;
        current->right->left = current->trc;
        current->right = NULL;
      }
    } else if (newNode->ttm < current->ttm) {
      if (prev->tlc == current) {
        prev->tlc = newNode;
      } else if (prev->trc == current) {
        prev->trc = newNode;
      }
      newNode->tp = prev;
      newNode->trc = current;
      current->tp = newNode;
      struct TreeInfo *secondNewNode =
          createLO_node(newNode->tId, newNode->ttm);
      newNode->tlc = secondNewNode;
      if (current->left != NULL) {
        secondNewNode->left = current->left;
        current->left->right = secondNewNode;
      }
      newNode->tlc->right = newNode->trc;
      newNode->trc->left = newNode->tlc;
    }
  }
  return root;
}

void printLO_BST(struct TreeInfo *root) {
  if (root == NULL)
    return;

  printLO_BST(root->tlc);
  printf("%d,", root->ttm);
  printLO_BST(root->trc);
}
void printSL(struct SubInfo *head) {
  struct SubInfo *current = head;
  printf("<");
  while (current != NULL) {
    (current->snext == NULL) ? printf("%d", current->sId)
                             : printf("%d,", current->sId);
    current = current->snext;
  }
  printf(">,");
}
struct SubInfo *SL_LookUp(struct SubInfo *head, int data) {
  struct SubInfo *current = head;
  while (current != NULL) {
    if (current->sId == data) {
      return current;
    }
    current = current->snext;
  }
  return NULL;
}
void SL_INSERT(struct SubInfo **head, struct SubInfo *new_sub) {
  struct SubInfo *temp = SL_LookUp(*head, new_sub->sId);
  if (temp != NULL) {
    printf("# Subscriber with Id %d already exists. #\n", new_sub->sId);
    free(new_sub);
    return;
  }

  if ((*head) == NULL || (*head)->sId >= new_sub->sId) {
    new_sub->snext = (*head);
    (*head) = new_sub;
  } else {
    struct SubInfo *current = (*head);
    while (current->snext != NULL && current->snext->sId < new_sub->sId) {
      current = current->snext;
    }
    new_sub->snext = current->snext;
    current->snext = new_sub;
  }
}

struct SubInfo *newSub(int id, int tm) {
  int i = 0;
  struct SubInfo *newNode = malloc(sizeof(struct SubInfo));
  newNode->sId = id;
  newNode->stm = tm;
  newNode->snext = NULL;
  for (i = 0; i < MG; i++) {
    newNode->sgp[i] = (struct TreeInfo *)1;
  }
  for (i = 0; i < MG; i++) {
    newNode->tgp[i] = (struct TreeInfo *)1;
  }
  return newNode;
}

void SL_DELETE(struct SubInfo **head, int id) {
  int i = 0;
  struct SubInfo *current = *head, *prev;
  if (current != NULL && current->sId == id) {
    for (int i = 0; i < MG; i++) {
      current->tgp[i] = NULL;
      current->sgp[i] = NULL;
    }
    (*head) = current->snext;
    return;
  }
  while (current != NULL && current->sId != id) {
    prev = current;
    current = current->snext;
  }
  /* in case we there is no such subscriber*/
  if (current == NULL) {
    printf("No such Subscriber\n");
    return;
  }
  /* in case there is:*/
  for (int i = 0; i < MG; i++) {
    current->tgp[i] = NULL;
    current->sgp[i] = NULL;
  }
  prev->snext = current->snext;
  return;
}
void hashPrint(struct SubInfo **hashtable, int m) {
  int i = 0;
  for (i = 0; i < m; i++) {
    if (hashtable[i] != NULL)
      printSL(hashtable[i]);
  }
}
int hashfunction(int key, int m, int p) {
  int pos = ((555 * key + 2000) * p) % m;
  return pos;
}

void hashInsert(struct SubInfo **hashtable, struct SubInfo *newSub, int m,
                int p) {
  int pos = hashfunction(newSub->sId, m, p);
  SL_INSERT(&hashtable[pos], newSub);
}

void hashDelete(struct SubInfo **hashtable, int sid, int m, int p) {
  int pos = hashfunction(sid, m, p);
  SL_DELETE(&hashtable[pos], sid);
}
struct SubInfo *hashLookUP(struct SubInfo **hashtable, int sid, int m, int p) {
  int pos = hashfunction(sid, m, p);
  return SL_LookUp(hashtable[pos], sid);
}
int L_LookUP(struct Subscription *head, int id) {
  struct Subscription *current = head;
  while (current != NULL) {
    if (current->sId == id) {
      return 1;
    }
    current = current->snext;
  }
  return 0;
}

int L_Insert(struct Subscription **head, int id) {

  if (L_LookUP(*head, id)) {
    return -1;
  }
  struct Subscription *newGSub = malloc(sizeof(struct Subscription));
  newGSub->sId = id;
  if (*head == NULL) {
    *head = newGSub;
    newGSub->snext = NULL;
  } else {
    newGSub->snext = *head;
    *head = newGSub;
  }
  return 1;
}

int L_Delete(struct Subscription **head, int id) {
  if (*head == NULL) {
    return 0;
  } else {
    struct Subscription *prev, *current = *head;
    if (current->sId == id) {
      *head = current->snext;
      return 2;
    }
    while (current != NULL && current->sId != id) {
      prev = current;
      current = current->snext;
    }
    if (current == NULL)
      return 1;

    prev->snext = current->snext;
    current->snext = NULL;
  }
  return 2;
}

void L_Print(struct Subscription *head) {
  struct Subscription *current = head;
  printf("<");
  while (current != NULL) {
    (current->snext == NULL) ? printf("%d", current->sId)
                             : printf("%d,", current->sId);
    current = current->snext;
  }
  printf(">\n");
}

int BST_Lookup(struct Info *root, int id) {
  if (root == NULL) {
    return 0;
  }
  struct Info *current = root;
  while (current != NULL) {
    if (id < current->iId)
      current = current->ilc;
    else if (id > current->iId)
      current = current->irc;
    else
      return 1;
  }
  return 0;
}
struct Info *printBST(struct Info *root) {
  if (root == NULL) {
    return NULL;
  }
  printBST(root->ilc);
  printf("%d,", root->iId);
  printBST(root->irc);
  return NULL;
}
struct Info *create_Info(int id, int tm) {
  int i = 0;
  struct Info *newNode = malloc(sizeof(struct Info));
  newNode->iId = id;
  newNode->itm = tm;
  newNode->ilc = NULL;
  newNode->irc = NULL;
  for (i = 0; i < MG; i++) {
    newNode->igp[i] = 0;
  }
  return newNode;
}

struct Info *BST_Insert(struct Info *root, struct Info *newInfo) {
  if (root == NULL) {
    return newInfo;
  }
  if (BST_Lookup(root, newInfo->iId)) {
    printf("Info with ID [%d] already exists. #BST_Insert\n", newInfo->iId);
    return root;
  }

  struct Info *current = root;
  struct Info *prev = root;
  while (current != NULL) {
    prev = current;
    if (current->iId < newInfo->iId)
      current = current->irc;
    else if (current->iId > newInfo->iId)
      current = current->ilc;
  }
  newInfo->ip = prev;
  if (newInfo->iId < prev->iId)
    prev->ilc = newInfo;
  else
    prev->irc = newInfo;

  return root;
}

struct Info *findNextGreater(struct Info *current) {
  while (current->ilc != NULL)
    current = current->ilc;
  return current;
}

struct Info *BST_Delete(struct Info *root, int id) {
  if (root == NULL) {
    return root;
  }
  if (!BST_Lookup(root, id)) {
    printf("Info with ID[%d] does not exists. #BST_Delete\n", id);
    return root;
  }
  /* find the node to be deleted */
  struct Info *current = root;
  while (current != NULL && current->iId != id) {
    if (id < current->iId)
      current = current->ilc;
    else if (id > current->iId)
      current = current->irc;
  }
  /* we found the node we want to delete */
  if (current != NULL) {
    /* Case 1: is leaf */
    if (current->ilc == NULL && current->irc == NULL) {
      if (current == root)
        root = NULL;
      else {
        if (current->ip->ilc == current)
          current->ip->ilc = NULL;
        else if (current->ip->irc == current)
          current->ip->irc = NULL;
      }
      // free(current);
      return root;
      /* Case 2: has two children */
    } else if (current->ilc != NULL && current->irc != NULL) {
      /* must find the next greater than current and change places */
      struct Info *nextGreater = findNextGreater(current->irc);
      /* store the info we need */
      int nextId = nextGreater->iId;
      int nextItm = nextGreater->itm;
      int nextArray[MG] = {0};
      for (int i = 0; i < MG; i++) {
        nextArray[i] = nextGreater->igp[i];
      }
      /* now we delete the nextGreater and then assign its values to current */
      root = BST_Delete(root, nextGreater->iId);
      current->iId = nextId;
      current->itm = nextItm;
      for (int i = 0; i < MG; i++) {
        current->igp[i] = nextArray[i];
      }
      return root;
    }
    /* Case 3: has 1 child */
    else {
      struct Info *child = NULL;
      if (current->ilc == NULL)
        child = current->irc;
      else
        child = current->ilc;

      if (current == root) {
        root = child;
      } else {
        if (current->ip->ilc == current)
          current->ip->ilc = child;
        else
          current->ip->irc = child;
      }
      child->ip = current->ip;
      // free(current);
      return root;
    }
  }
  return root;
}
/**
 * @brief Optional function to initialize data structures that
 *        need initialization
 *
 * @param m Size of the hash table.
 * @param p Prime number for the universal hash functions.
 *
 * @return 0 on success
 *         1 on failure
 */
int initialize(int m, int p) {

  int i = 0;
  for (i = 0; i < MG; i++) {
    groups[i].gId = i;
    groups[i].gr = NULL;
    groups[i].gsub = NULL;
  }
  return EXIT_SUCCESS;
}

/**
 * @brief Free resources
 *
 * @return 0 on success
 *         1 on failure
 */
int free_all(void) { return EXIT_SUCCESS; }

/**
 * @brief Insert info
 *
 * @param iTM Timestamp of arrival
 * @param iId Identifier of information
 * @param gids_arr Pointer to array containing the gids of the Event.
 * @param size_of_gids_arr Size of gids_arr including -1
 * @return 0 on success
 *          1 on failure
 */
int Insert_Info(int iTM, int iId, int *gids_arr, int size_of_gids_arr) {
  int i = 0;
  int j = 0;
  for (i = 0; i < MG; i++) {
    if (BST_Lookup(groups[i].gr, iId)) {
      printf("# INFOID <%d> exists in GROUPID <%d> #\n", iId, i);
      return EXIT_FAILURE;
    }
  }
  for (j = 0; j < size_of_gids_arr - 1; j++) {
    struct Info *new_info = create_Info(iId, iTM);
    for (i = 0; i < size_of_gids_arr - 1; i++) {
      if (gids_arr[i] >= MG) {
        printf("# GROUPID = <%d> is WRONG. #\n", gids_arr[i]);
      } else {
        new_info->igp[gids_arr[i]] = 1;
      }
    }
    if (!(gids_arr[i] >= MG)) {
      groups[gids_arr[j]].gr = BST_Insert(groups[gids_arr[j]].gr, new_info);
    }
  }
  return EXIT_SUCCESS;
}
/**
 * @brief Subsriber Registration
 *
 * @param sTM Timestamp of arrival
 * @param sId Identifier of subscriber
 * @param gids_arr Pointer to array containing the gids of the Event.
 * @param size_of_gids_arr Size of gids_arr including -1
 * @return 0 on success
 *          1 on failure
 */
int Subscriber_Registration(struct SubInfo **hashtable, int sTM, int sId,
                            int *gids_arr, int size_of_gids_arr, int m, int p) {

  int i = 0;
  int j = 0;
  struct SubInfo *new_sub = newSub(sId, sTM);
  for (j = 0; j < size_of_gids_arr - 1; j++) {
    for (i = 0; i < size_of_gids_arr - 1; i++) {
      if (gids_arr[i] >= MG) {
        printf("# GROUPID = <%d> is invalid. #\n", gids_arr[i]);
      } else {
        new_sub->tgp[gids_arr[i]] = NULL;
        new_sub->sgp[gids_arr[i]] = new_sub->tgp[gids_arr[i]];
      }
    }
    /* Insert to the group list sub */
    if (!(gids_arr[j] >= MG))
      if (L_Insert(&groups[gids_arr[j]].gsub, sId) == -1) {
        printf("# SubId <%d> already exists in GROUPID = <%d>. #\n", sId,
               groups[gids_arr[j]].gId);
      }
  }

  /* Insert to SubInfo list */
  hashInsert(hashtable, new_sub, m, p);

  return EXIT_SUCCESS;
}
/**
 * @brief Prune Information from server and forward it to client
 *
 * @param tm Information timestamp of arrival
 * @return 0 on success
 *          1 on failure
 */
void PruneHelp(struct SubInfo **hashtable, struct Info *root, int tm,
               int groupID, int m, int p) {
  if (root == NULL) {
    return;
  }
  struct Info *templeft = root->ilc;
  struct Info *tempright = root->irc;
  if (root->itm <= tm) {
    struct TreeInfo *newTreeInfo = createLO_node(root->iId, root->itm);
    struct Subscription *current = groups[groupID].gsub;
    struct SubInfo *currentSubhash = NULL;
    while (current != NULL) {
      currentSubhash = hashLookUP(hashtable, current->sId, m, p);
      if (currentSubhash->tgp[groupID] != (struct TreeInfo *)1)
        currentSubhash->tgp[groupID] =
            LO_BST_Insert(currentSubhash->tgp[groupID], newTreeInfo);
      current = current->snext;
    }
    groups[groupID].gr = BST_Delete(groups[groupID].gr, root->iId);
    if (BST_Lookup(groups[groupID].gr, root->iId) && root->itm <= tm)
      PruneHelp(hashtable, root, tm, groupID, m, p);
  }

  PruneHelp(hashtable, templeft, tm, groupID, m, p);
  PruneHelp(hashtable, tempright, tm, groupID, m, p);
  return;
}
int Prune(struct SubInfo **hashtable, int m, int p, int tm) {
  int i = 0;
  for (i = 0; i < MG; i++) {
    if (groups[i].gr != NULL)
      PruneHelp(hashtable, groups[i].gr, tm, i, m, p);
  }

  return EXIT_SUCCESS;
}
/**
 * @brief Consume Information for subscriber
 *
 * @param sId Subscriber identifier
 * @return 0 on success
 *          1 on failure
 */
int Consume(int sId, struct SubInfo **hashtable, int m, int p) {

  int i = 0;
  int j = 0;
  struct SubInfo *curSub = hashLookUP(hashtable, sId, m, p);
  if (curSub != NULL) {
    for (i = 0; i < MG; i++) {
      if (curSub->tgp[i] != (struct TreeInfo *)1 && curSub->tgp[i] != NULL) {
        if (curSub->sgp[i] == NULL)
          curSub->sgp[i] = curSub->tgp[i];
        if (curSub->sgp[i] != NULL) {
          while (curSub->sgp[i]->tlc != NULL) {
            curSub->sgp[i] = curSub->sgp[i]->tlc;
          }
          printf("GROUPID = <%d>, TREELIST = <", i);
          printLOList(curSub->sgp[i]);
          while (curSub->sgp[i]->right != NULL)
            curSub->sgp[i] = curSub->sgp[i]->right;

          printf(">, NEWSGP <%d>\n", curSub->sgp[i]->tId);
        }
      }
    }
  }
  return EXIT_SUCCESS;
}
/**
 * @brief Delete subscriber
 *
 * @param sId Subscriber identifier
 * @return 0 on success
 *          1 on failure
 */
int Delete_Subscriber(struct SubInfo **hashtable, int sId, int m, int p) {

  int i = 0;
  if (hashLookUP(hashtable, sId, m, p) != NULL) {
    hashDelete(hashtable, sId, m, p);
    printf("SUBSCRIBERLIST = ");
    hashPrint(hashtable, m);
    printf("\n");
    for (i = 0; i < MG; i++)
      if ((L_Delete(&groups[i].gsub, sId) == 2)) {
        printf("GROUPID = <%d>, SUBLIST = ", groups[i].gId);
        L_Print(groups[i].gsub);
      }
  } else {
    printf("# Subscriber with ID <%d> does not exists.\n", sId);
    return EXIT_FAILURE;
  }

  return EXIT_SUCCESS;
}
/**
 * @brief Print Data Structures of the system
 *
 * @return 0 on success
 *          1 on failure
 */
int Print_all(struct SubInfo **hashtable, int m) {
  int i = 0;
  for (i = 0; i < MG; i++) {
    printf("GROUPID = <%d>, INFOLIST = ", i);
    printBST(groups[i].gr);
    printf(" SUBLIST = ");
    L_Print(groups[i].gsub);
  }
  printf("SUBSCRIBERLIST = ");
  hashPrint(hashtable, m);
  printf("\n");
  for (i = 0; i < m; i++) {
    if (hashtable[i] != NULL) {
      printForEach(hashtable[i]);
    }
  }
  int numberOfSubs = calcNumber(hashtable, m);
  printf("NO_GROUPS = <%d>, NO_SUBSCRIBERS = <%d>\n", MG, numberOfSubs);

  return EXIT_SUCCESS;
}
